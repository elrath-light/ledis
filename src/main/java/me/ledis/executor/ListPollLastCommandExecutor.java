package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

import static me.ledis.constant.ResponseMessage.KEY_IS_NOT_LIST_ERROR_MESSAGE;
import static me.ledis.constant.ResponseMessage.RPOP_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ListPollLastCommandExecutor extends CommandExecutor {
    public ListPollLastCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, RPOP_SYNTAX_ERROR_MESSAGE);
        }
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        if (value == null) {
            return null;
        }
        boolean valueIsNotList = !(value instanceof List);
        if (valueIsNotList) {
            throw new ResponseStatusException(BAD_REQUEST, KEY_IS_NOT_LIST_ERROR_MESSAGE);
        }
        LinkedList<String> values = (LinkedList<String>) value;
        return values.pollLast();
    }
}
