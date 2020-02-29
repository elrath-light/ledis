package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SCARD_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SetLengthCommandExecutor extends CommandExecutor {
    public SetLengthCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, SCARD_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return String.valueOf(0);
        }
        boolean valueIsNotSet = !(value instanceof Set);
        if (valueIsNotSet) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_SET_ERROR_MESSAGE);
        }
        Set<String> values = (Set<String>) value;
        return String.valueOf(values.size());
    }
}
