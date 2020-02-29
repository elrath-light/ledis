package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static me.ledis.constant.ResponseMessage.KEYS_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DataGetAllKeysCommandExecutor extends CommandExecutor {
    public DataGetAllKeysCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 1) {
            throw new ResponseStatusException(BAD_REQUEST, KEYS_SYNTAX_ERROR_MESSAGE);
        }
        return Arrays.deepToString(LedisData.getAllKeys());
    }
}
