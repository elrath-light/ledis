package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SADD_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetAddCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    @SuppressWarnings("unchecked")
    public void values_should_be_stored_correctly() {
        executor = getFactory().createByCommand("SADD mySet one two three");
        assertThat(executor.execute())
                .isEqualTo("3");
        Set<String> mySet = (Set<String>) LedisData.get("mySet");
        assertThat(mySet).hasSize(3);

        executor = getFactory().createByCommand("SADD mySet one two");
        assertThat(executor.execute())
                .isEqualTo("0");
        assertThat(mySet).hasSize(3);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("SADD mySet");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(SADD_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_set() {
        initSampleList();
        executor = getFactory().createByCommand("SADD elements one");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(KEY_IS_NOT_SET_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
