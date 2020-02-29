package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class StringSetCommandExecutor extends CommandExecutor {
    public StringSetCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        if (getCommandSegments().length != 3) {
            throw new ResponseStatusException(BAD_REQUEST, "Syntax ERROR: the correct syntax is SET <key> <value>");
        }
        String key = getCommandSegments()[1];
        String value = getCommandSegments()[2];
        LedisData.set(key, value);
        return "OK";
    }
}
