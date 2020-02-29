package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.FLUSHDB_SYNTAX_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.OK_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DataCleanUpCommandExecutor extends CommandExecutor {
    public DataCleanUpCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 1) {
            throw new ResponseStatusException(BAD_REQUEST, FLUSHDB_SYNTAX_ERROR_MESSAGE);
        }
        LedisData.clear();
        LedisData.clearAllExpireTime();
        return OK_MESSAGE;
    }
}
