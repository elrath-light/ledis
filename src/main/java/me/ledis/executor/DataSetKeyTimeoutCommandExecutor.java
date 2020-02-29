package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static me.ledis.constant.ResponseMessage.TIMEOUT_NOT_LONG_OR_INT_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.EXPIRE_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DataSetKeyTimeoutCommandExecutor extends CommandExecutor {
    public DataSetKeyTimeoutCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 3) {
            throw new ResponseStatusException(BAD_REQUEST, EXPIRE_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return String.valueOf(0);
        }
        String timeoutInSeconds = getCommandSegments()[2];
        try {
            LocalDateTime expireTime = LocalDateTime.now()
                    .plusSeconds(Long.parseLong(timeoutInSeconds));
            LedisData.setExpireTime(key, expireTime);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(BAD_REQUEST, TIMEOUT_NOT_LONG_OR_INT_ERROR_MESSAGE);
        }
        return timeoutInSeconds;
    }
}
