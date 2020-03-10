package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SetGetAllCommandExecutor extends CommandExecutor {
    public SetGetAllCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return EMPTY_SET_MESSAGE;
        }
        boolean valueIsNotSet = !(value instanceof Set);
        if (valueIsNotSet) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_SET_ERROR_MESSAGE);
        }
        Set<String> values = (Set<String>) value;
        return Arrays.deepToString(values.toArray());
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, SMEMBERS_SYNTAX_ERROR_MESSAGE);
        }
    }
}
