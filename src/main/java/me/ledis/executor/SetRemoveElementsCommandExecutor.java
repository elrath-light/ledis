package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SREM_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SetRemoveElementsCommandExecutor extends CommandExecutor {
    public SetRemoveElementsCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        if (getCommandSegments().length < 3) {
            throw new ResponseStatusException(BAD_REQUEST, SREM_SYNTAX_ERROR_MESSAGE);
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
        return removeElements(values);
    }

    private String removeElements(Set<String> values) {
        int preRemoveElementsCount = values.size();
        String key = getCommandSegments()[1];
        for (int i = 2; i < getCommandSegments().length; i++) {
            String value = getCommandSegments()[i];
            values.remove(value);
        }
        LedisData.set(key, values);
        int postRemoveElementsCount = values.size();
        return String.valueOf(preRemoveElementsCount - postRemoveElementsCount);
    }
}
