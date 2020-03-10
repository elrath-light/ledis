package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.OK_MESSAGE;
import static me.ledis.constant.ResponseMessage.STRING_SET_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringSetCommandExecutorTest extends LedisTest {

    @Test
    public void string_should_be_stored_and_overridden_correctly() {
        String command = "   set          me     Good    ";
        CommandExecutor executor = getFactory().createByCommand(command);
        assertThat(executor.execute()).isEqualTo(OK_MESSAGE);
        Object me = LedisData.get("me");
        assertThat(me).isInstanceOf(String.class);
        assertThat(me).isEqualTo("Good");

        executor = getFactory().createByCommand("SET me Better everyday");
        executor.execute();
        Object newMe = LedisData.get("me");
        assertThat(newMe).isEqualTo("Better everyday");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        assertThatThrownBy(() -> getFactory().createByCommand("Set"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(STRING_SET_SYNTAX_ERROR_MESSAGE);

        assertThatThrownBy(() -> getFactory().createByCommand("set bad"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(STRING_SET_SYNTAX_ERROR_MESSAGE);
    }
}
