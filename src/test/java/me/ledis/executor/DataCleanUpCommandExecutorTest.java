package me.ledis.executor;

import me.ledis.LedisTest;
import me.ledis.data.LedisData;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.FLUSHDB_SYNTAX_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.OK_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataCleanUpCommandExecutorTest extends LedisTest {
    private CommandExecutor executor;

    @Test
    public void data_should_be_cleaned_up_correctly() {
        initSampleList();
        initSampleSet();
        executor = getFactory().createByCommand("FLUSHDB");
        assertThat(executor.execute())
                .isEqualTo(OK_MESSAGE);
        assertThat(LedisData.get("elements"))
                .isNull();
        assertThat(LedisData.get("mySet"))
                .isNull();
    }

    @Test
    public void exception_should_be_thrown_when_syntax_is_incorrect() {
        executor = getFactory().createByCommand("FLUSHDB a");
        assertThatThrownBy(() -> executor.execute())
                .hasMessageContaining(FLUSHDB_SYNTAX_ERROR_MESSAGE)
                .isInstanceOf(ResponseStatusException.class);
    }
}
