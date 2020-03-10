package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static me.ledis.constant.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataDeleteKeyCommandExecutorTest extends LedisTest {

    private CommandExecutor executor;

    @Test
    public void key_and_its_corresponding_value_should_be_removed_correctly() {
        initSampleList();
        String myListKey = "elements";
        LedisData.setExpireTime(myListKey, LocalDateTime.now().plusHours(1));
        executor = getFactory().createByCommand("DEL elements");
        assertThat(executor.execute())
                .isEqualTo(OK_MESSAGE);
        assertThat(LedisData.get(myListKey))
                .isNull();
        assertThat(LedisData.getExpireTime(myListKey))
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
        assertThatThrownBy(() -> getFactory().createByCommand("DEL a b"))
                .hasMessageContaining(DEL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
        assertThatThrownBy(() -> getFactory().createByCommand("DEL"))
                .hasMessageContaining(DEL_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
