package me.ledis.executor;

import me.ledis.LedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetGetAllCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void all_elements_should_be_retrieved_correctly() {
        initSampleSet();
        executor = getFactory().createByCommand("SMEMBERS mySet");
        assertThat(executor.execute())
                .contains("one", "two", "three");

        executor = getFactory().createByCommand("SMEMBERS non-existent");
        assertThat(executor.execute())
                .isEqualTo(EMPTY_SET_MESSAGE);
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_set() {
        initSampleList();
        executor = getFactory().createByCommand("SMEMBERS elements");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(KEY_IS_NOT_SET_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        assertThatThrownBy(() -> getFactory().createByCommand("SMEMBERS a b"))
                .hasMessageContaining(SMEMBERS_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
