package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.STRING_GET_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringGetCommandExecutorTest extends LedisTest {
    @Test
    public void string_should_be_retrieved_correctly() {
        LedisData.set("you", "Good");
        CommandExecutor executor = getFactory().createByCommand("GET you");
        assertThat(executor.execute())
                .isEqualTo("Good");

        executor = getFactory().createByCommand("GET non-existent");
        assertThat(executor.execute())
                .isNull();
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        assertThatThrownBy(() -> getFactory().createByCommand("GET a b"))
                .hasMessageContaining(STRING_GET_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        assertThatThrownBy(() -> getFactory().createByCommand("GET"))
                .hasMessageContaining(STRING_GET_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
