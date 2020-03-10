package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_SET_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.SADD_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SetAddCommandExecutor extends CommandExecutor {
    public SetAddCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return addElements(new HashSet<>());
        }
        boolean valueIsNotSet = !(value instanceof Set);
        if (valueIsNotSet) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_SET_ERROR_MESSAGE);
        }
        Set<String> values = (Set<String>) value;
        return addElements(values);
    }

    private String addElements(Set<String> values) {
        int preAddElementsCount = values.size();
        String key = getCommandSegments()[1];
        for (int i = 2; i < getCommandSegments().length; i++) {
            String value = getCommandSegments()[i];
            values.add(value);
        }
        LedisData.set(key, values);
        int postAddElementsCount = values.size();
        return String.valueOf(postAddElementsCount - preAddElementsCount);
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length < 3) {
            throw new ResponseStatusException(BAD_REQUEST, SADD_SYNTAX_ERROR_MESSAGE);
        }
    }
}
