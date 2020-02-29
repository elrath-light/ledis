package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static me.ledis.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DataGetKeyTimeoutCommandExecutor extends CommandExecutor {
    public DataGetKeyTimeoutCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, TTL_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        if (LedisData.get(key) == null) {
            return KEY_IS_NOT_EXISTED_MESSAGE;
        }
        LocalDateTime expireTime = LedisData.getExpireTime(key);
        return String.format(KEY_WILL_BE_REMOVED_AT_MESSAGE, expireTime);
    }
}
