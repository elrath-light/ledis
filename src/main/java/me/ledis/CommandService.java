package me.ledis;

import me.ledis.executor.CommandExecutor;
import me.ledis.factory.CommandExecutorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    private CommandExecutorFactory factory = new CommandExecutorFactory();

    public String execute(String command) {
        CommandExecutor executor = factory.createByCommand(command);
        if (executor == null) {
            return StringUtils.EMPTY;
        }
        return executor.execute();
    }
}
