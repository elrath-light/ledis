package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.ledis.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ListRangeCommandExecutor extends CommandExecutor {
    public ListRangeCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        if (getCommandSegments().length != 4) {
            throw new ResponseStatusException(BAD_REQUEST, LRANGE_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return EMPTY_LIST_MESSAGE;
        }
        boolean valueIsNotList = !(value instanceof List);
        if (valueIsNotList) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_LIST_ERROR_MESSAGE);
        }
        List<String> values = (List<String>) value;
        int start = 0;
        int stop = 0;
        try {
            start = Integer.parseInt(getCommandSegments()[2]);
            stop = Integer.parseInt(getCommandSegments()[3]);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, LRANGE_NOT_INTEGER_ERROR_MESSAGE);
        }
        if (start > values.size()) {
            return EMPTY_LIST_MESSAGE;
        }
        if (stop >= values.size()) {
            stop = values.size() - 1;
        }
        List<String> result = new ArrayList<>();
        for (int i = start; i <= stop; i++) {
            String currentValue = values.get(i);
            result.add(currentValue);
        }
        return Arrays.deepToString(result.toArray());
    }
}
