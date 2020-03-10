package me.ledis.executor;

import com.google.common.collect.Sets;
import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SINTER_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SetIntersectionCommandExecutor extends CommandExecutor {
    public SetIntersectionCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        Set<String> intersectionSet = safeCast(value);
        for (int i = 2; i < getCommandSegments().length; i++) {
            String currentKey = getCommandSegments()[i];
            Set<String> currentValues = safeCast(LedisData.get(currentKey));
            intersectionSet = Sets.intersection(intersectionSet, currentValues);
        }
        return Arrays.deepToString(intersectionSet.toArray());
    }

    @SuppressWarnings("unchecked")
    private Set<String> safeCast(Object value) {
        if (value == null) {
            return Collections.emptySet();
        }
        boolean valueIsNotSet = !(value instanceof Set);
        if (valueIsNotSet) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_SET_ERROR_MESSAGE);
        }
        return (Set<String>) value;
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length < 2) {
            throw new ResponseStatusException(BAD_REQUEST, SINTER_SYNTAX_ERROR_MESSAGE);
        }
    }
}
