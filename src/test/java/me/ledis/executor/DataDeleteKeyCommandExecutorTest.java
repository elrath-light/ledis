package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataDeleteKeyCommandExecutorTest extends LedisTest {

    private CommandExecutor executor;

    @Test
    public void key_and_its_corresponding_value_should_be_removed_correctly() {
        initSampleList();
        executor = getFactory().createByCommand("DEL elements");
        assertThat(executor.execute())
                .isEqualTo(OK_MESSAGE);
        assertThat(LedisData.get("elements"))
                .isNull();
    }

    @Test
    public void no_deletion_occurred_when_key_is_non_existent() {
        initSampleList();
        executor = getFactory().createByCommand("DEL non-existent");
        assertThat(executor.execute())
                .isEqualTo(KEY_IS_NOT_EXISTED_MESSAGE);
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("DEL a b");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(DEL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
        executor = getFactory().createByCommand("DEL");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(DEL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
