package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_LIST_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.LLEN_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListLengthCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;
    @Test
    public void length_of_list_should_be_retrieved_correctly() {
        List<String> myList = new ArrayList<>();
        myList.add("one");
        myList.add("two");
        LedisData.set("myList", myList);
        executor = getFactory().createByCommand("LLEN myList");
        assertThat(executor.execute())
                .isEqualTo("2");
    }

    @Test
    public void zero_is_length_of_non_existent_list() {
        executor = getFactory().createByCommand("LLEN non-existent");
        assertThat(executor.execute())
                .isEqualTo("0");
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_list() {
        Set<String> mySet = new HashSet<>();
        mySet.add("one");
        LedisData.set("mySet", mySet);
        executor = getFactory().createByCommand("LLEN mySet");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(KEY_IS_NOT_LIST_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("LLEN a b");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LLEN_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        executor = getFactory().createByCommand("LLEN");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LLEN_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
