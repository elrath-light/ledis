package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DataDeleteKeyCommandExecutor extends CommandExecutor {
    public DataDeleteKeyCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, DEL_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object removedValue = LedisData.remove(key);
        LedisData.removeExpireTime(key);
        return removedValue == null ? KEY_IS_NOT_EXISTED_MESSAGE : OK_MESSAGE;
    }
}
