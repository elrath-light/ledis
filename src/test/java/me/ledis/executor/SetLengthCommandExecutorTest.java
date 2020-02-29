package me.ledis.executor;

import me.ledis.LedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SCARD_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetLengthCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;
    @Test
    public void length_of_set_should_be_retrieved_correctly() {
        initSampleSet();
        executor = getFactory().createByCommand("SCARD mySet");
        assertThat(executor.execute())
                .isEqualTo("3");
    }

    @Test
    public void zero_is_length_of_non_existent_set() {
        executor = getFactory().createByCommand("SCARD non-existent");
        assertThat(executor.execute())
                .isEqualTo("0");
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_set() {
        initSampleList();
        executor = getFactory().createByCommand("SCARD elements");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(KEY_IS_NOT_SET_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("SCARD a b");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(SCARD_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
