package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetRemoveElementsCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    @SuppressWarnings("unchecked")
    public void elements_should_be_removed_correctly() {
        initSampleSet();
        executor = getFactory().createByCommand("SREM mySet one three");
        assertThat(executor.execute())
                .isEqualTo("2");
        Set<String> mySet = (Set<String>) LedisData.get("mySet");
        assertThat(mySet).containsExactly("two");

        executor = getFactory().createByCommand("SREM non-existent one");
        assertThat(executor.execute())
                .isEqualTo("0");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("SREM mySet");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(SREM_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_set() {
        initSampleList();
        executor = getFactory().createByCommand("SREM elements one");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(KEY_IS_NOT_SET_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
