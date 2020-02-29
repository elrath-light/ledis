package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

import static me.ledis.constant.ResponseMessage.LPOP_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListPollFirstCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    @SuppressWarnings("unchecked")
    public void first_element_should_be_retrieved_and_removed_correctly() {
        initSampleList();
        executor = getFactory().createByCommand("LPOP elements");
        assertThat(executor.execute())
                .isEqualTo("one");
        List<String> elements = (List<String>) LedisData.get("elements");
        assertThat(elements)
                .containsExactly("two", "three");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("LPOP");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LPOP_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        executor = getFactory().createByCommand("LPOP key value");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(LPOP_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
