package me.ledis.executor;

import me.ledis.LedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.KEYS_SYNTAX_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataGetAllKeysCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void all_keys_should_be_retrieved_correctly() {
        initSampleList();
        initSampleSet();
        executor = getFactory().createByCommand("KEYS");
        assertThat(executor.execute())
                .contains("mySet", "elements")
                .doesNotContain("noSet");
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("KEYS a");
        assertThatThrownBy(() -> executor.execute())
                .withFailMessage(KEYS_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
