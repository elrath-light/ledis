package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class DataSetKeyTimeoutCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;
    @Test
    public void timeout_should_be_set_and_expired_key_should_be_removed() throws InterruptedException {
        initSampleSet();
        executor = getFactory().createByCommand("EXPIRE mySet 1");
        assertThat(executor.execute())
                .isEqualTo("1");
        Thread.sleep(2000L);
        String mySetKey = "mySet";
        assertThat(LedisData.get(mySetKey))
                .isNull();
        assertThat(LedisData.getExpireTime(mySetKey))
                .isNull();
    }

    @Test
    public void no_timeout_is_set_when_key_is_not_existent() {
        executor = getFactory().createByCommand("EXPIRE noSet 1");
        assertThat(executor.execute())
                .isEqualTo("0");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("EXPIRE noSet");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(EXPIRE_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_timeout_is_not_a_number() {
        initSampleSet();
        executor = getFactory().createByCommand("EXPIRE mySet one");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(TIMEOUT_NOT_LONG_OR_INT_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
