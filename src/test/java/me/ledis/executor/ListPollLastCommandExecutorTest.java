package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

import static me.ledis.constant.ResponseMessage.RPOP_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListPollLastCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    @SuppressWarnings("unchecked")
    public void last_element_should_be_retrieved_and_removed_correctly() {
        initSampleList();
        executor = getFactory().createByCommand("RPOP elements");
        assertThat(executor.execute())
                .isEqualTo("three");
        List<String> elements = (List<String>) LedisData.get("elements");
        assertThat(elements)
                .containsExactly("one", "two");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("RPOP");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(RPOP_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        executor = getFactory().createByCommand("RPOP key value");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(RPOP_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
