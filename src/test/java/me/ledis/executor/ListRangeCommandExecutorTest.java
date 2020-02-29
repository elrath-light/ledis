package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListRangeCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void elements_between_range_should_be_retrieved_correctly() {
        initSampleList();
        executor = getFactory().createByCommand("LRANGE elements 0 0");
        assertThat(executor.execute())
                .isEqualTo("[one]");

        executor = getFactory().createByCommand("LRANGE elements 5 10");
        assertThat(executor.execute())
                .isEqualTo("empty list");

        executor = getFactory().createByCommand("LRANGE elements 0 100");
        assertThat(executor.execute())
                .isEqualTo("[one, two, three]");

        executor = getFactory().createByCommand("LRANGE elements 0 3");
        assertThat(executor.execute())
                .isEqualTo("[one, two, three]");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("LRANGE elements");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LRANGE_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_index_range_not_integer() {
        initSampleList();
        executor = getFactory().createByCommand("LRANGE elements zero two");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LRANGE_NOT_INTEGER_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_list() {
        Set<String> mySet = new HashSet<>();
        mySet.add("one");
        LedisData.set("mySet", mySet);
        executor = getFactory().createByCommand("LRANGE mySet");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(KEY_IS_NOT_LIST_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
