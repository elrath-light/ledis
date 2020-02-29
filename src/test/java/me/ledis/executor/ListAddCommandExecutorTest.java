package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_LIST_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.RPUSH_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListAddCommandExecutorTest extends LedisTest {

    private CommandExecutor executor;

    @Test
    public void elements_should_be_added_correctly() {
        executor = getFactory().createByCommand("RPUSH myList one two three");
        assertThat(executor.execute())
                .isEqualTo("3");

        executor = getFactory().createByCommand("RPUSH myList four");
        assertThat(executor.execute())
                .isEqualTo("4");
    }

    @Test
    public void exception_should_be_thrown_when_key_is_not_a_list() {
        Set<String> mySet = new HashSet<>();
        mySet.add("one");
        LedisData.set("mySet", mySet);
        executor = getFactory().createByCommand("RPUSH mySet");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(KEY_IS_NOT_LIST_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("RPUSH myList");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(RPUSH_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        executor = getFactory().createByCommand("RPUSH");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(RPUSH_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
