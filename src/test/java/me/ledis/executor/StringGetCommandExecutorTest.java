package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.STRING_GET_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringGetCommandExecutorTest extends LedisTest {
    private CommandExecutor getCommandExecutor;
    @Test
    public void string_should_be_retrieved_correctly() {
        LedisData.set("you", "Good");
        getCommandExecutor = getFactory().createByCommand("GET you");
        assertThat(getCommandExecutor.execute())
                .isEqualTo("Good");

        getCommandExecutor = getFactory().createByCommand("GET non-existent");
        assertThat(getCommandExecutor.execute())
                .isNull();
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        getCommandExecutor = getFactory().createByCommand("GET a b");
        assertThatThrownBy(() -> getCommandExecutor.execute())
                .withFailMessage(STRING_GET_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);

        getCommandExecutor = getFactory().createByCommand("GET");
        assertThatThrownBy(() -> getCommandExecutor.execute())
                .withFailMessage(STRING_GET_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
