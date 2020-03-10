package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.STRING_GET_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class StringGetCommandExecutor extends CommandExecutor {
    public StringGetCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        String key = getCommandSegments()[1];
        Object value = LedisData.get(key);
        return value == null ? null : value.toString();
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length != 2) {
            throw new ResponseStatusException(BAD_REQUEST, STRING_GET_SYNTAX_ERROR_MESSAGE);
        }
    }
}
