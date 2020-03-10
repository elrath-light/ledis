package me.ledis.executor;

import me.ledis.data.LedisData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.StringJoiner;

import static me.ledis.constant.ResponseMessage.STRING_SET_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class StringSetCommandExecutor extends CommandExecutor {
    public StringSetCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        String key = getCommandSegments()[1];
        StringJoiner joiner = new StringJoiner(StringUtils.SPACE);
        for (int i = 2; i < getCommandSegments().length; i++) {
            joiner.add(getCommandSegments()[i]);
        }
        LedisData.set(key, joiner.toString());
        return "OK";
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length < 3) {
            throw new ResponseStatusException(BAD_REQUEST, STRING_SET_SYNTAX_ERROR_MESSAGE);
        }
    }
}
