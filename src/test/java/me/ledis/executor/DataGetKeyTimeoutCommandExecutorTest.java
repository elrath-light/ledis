package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataGetKeyTimeoutCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void expire_time_for_key_should_be_retrieved_correctly() {
        initSampleSet();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(5);
        LedisData.setExpireTime("mySet", expireTime);
        executor = getFactory().createByCommand("TTL mySet");
        assertThat(executor.execute())
                .isEqualTo(String.format(KEY_WILL_BE_REMOVED_AT_MESSAGE, expireTime));

        initSampleList();
        executor = getFactory().createByCommand("TTL elements");
        assertThat(executor.execute())
                .isEqualTo(NO_TIMEOUT_MESSAGE);
    }

    @Test
    public void no_timeout_for_non_existent_key() {
        executor = getFactory().createByCommand("TTL non-existent");
        assertThat(executor.execute())
                .isEqualTo(KEY_IS_NOT_EXISTED_MESSAGE);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("TTL a b");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(TTL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
        executor = getFactory().createByCommand("TTL");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(TTL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
