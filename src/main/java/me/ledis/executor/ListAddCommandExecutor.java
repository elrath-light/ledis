package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_LIST_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.RPUSH_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ListAddCommandExecutor extends CommandExecutor {
    public ListAddCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        if (getCommandSegments().length < 3) {
            throw new ResponseStatusException(BAD_REQUEST, RPUSH_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return addElements(new LinkedList<>());
        }
        boolean valueIsNotList = !(value instanceof List);
        if (valueIsNotList) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_LIST_ERROR_MESSAGE);
        }
        List<String> existingValues = (List<String>) value;
        return addElements(existingValues);
    }

    private String addElements(List<String> values) {
        String key = getCommandSegments()[1];
        for (int i = 2; i < getCommandSegments().length; i++) {
            values.add(getCommandSegments()[i]);
        }
        LedisData.set(key, values);
        return String.valueOf(values.size());
    }
}
