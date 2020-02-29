package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_LIST_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.LLEN_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ListLengthCommandExecutor extends CommandExecutor {
    public ListLengthCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, LLEN_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return String.valueOf(0);
        }
        boolean valueIsNotList = !(value instanceof List);
        if (valueIsNotList) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_LIST_ERROR_MESSAGE);
        }
        List<?> values = (List<?>) value;
        return String.valueOf(values.size());
    }
}
