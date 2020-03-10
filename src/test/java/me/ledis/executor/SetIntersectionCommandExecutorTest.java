package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SINTER_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetIntersectionCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void intersection_between_sets_should_be_retrieved_correctly() {
        initSampleSet();
        Set<String> yourSet = new HashSet<>();
        yourSet.add("four");
        yourSet.add("three");
        yourSet.add("two");
        LedisData.set("yourSet", yourSet);
        executor = getFactory().createByCommand("SINTER mySet yourSet");
        assertThat(executor.execute())
                .contains("two", "three")
                .doesNotContain("one", "four");

        executor = getFactory().createByCommand("SINTER mySet noSet");
        assertThat(executor.execute())
                .isEqualTo("[]");
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_set() {
        initSampleList();
        executor = getFactory().createByCommand("SINTER elements");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(KEY_IS_NOT_SET_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        assertThatThrownBy(() -> getFactory().createByCommand("SINTER "))
                .hasMessageContaining(SINTER_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
