package me.ledis.factory;

import me.ledis.executor.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.UNSUPPORTED_COMMAND_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CommandExecutorFactory {
    public CommandExecutor createByCommand(String command) {
        String trimmedCommand = StringUtils.trimToNull(command);
        if (trimmedCommand == null) {
            return null;
        }
        String[] commandSegments = StringUtils.split(trimmedCommand, StringUtils.SPACE);
        String commandName = commandSegments[0].toUpperCase();
        switch (commandName) {
            case "GET":
                return new StringGetCommandExecutor(commandSegments);
            case "SET":
                return new StringSetCommandExecutor(commandSegments);
            case "LLEN":
                return new ListLengthCommandExecutor(commandSegments);
            case "RPUSH":
                return new ListAddCommandExecutor(commandSegments);
            case "LPOP":
                return new ListPollFirstCommandExecutor(commandSegments);
            case "RPOP":
                return new ListPollLastCommandExecutor(commandSegments);
            case "LRANGE":
                return new ListRangeCommandExecutor(commandSegments);
            case "SADD":
                return new SetAddCommandExecutor(commandSegments);
            case "SCARD":
                return new SetLengthCommandExecutor(commandSegments);
            case "SMEMBERS":
                return new SetGetAllCommandExecutor(commandSegments);
            case "SREM":
                return new SetRemoveElementsCommandExecutor(commandSegments);
            case "SINTER":
                return new SetIntersectionCommandExecutor(commandSegments);
            case "KEYS":
                return new DataGetAllKeysCommandExecutor(commandSegments);
            case "DEL":
                return new DataDeleteKeyCommandExecutor(commandSegments);
            case "FLUSHDB":
                return new DataCleanUpCommandExecutor(commandSegments);
            case "EXPIRE":
                return new DataSetKeyTimeoutCommandExecutor(commandSegments);
            case "TTL":
                return new DataGetKeyTimeoutCommandExecutor(commandSegments);
            case "SAVE":
                return new SnapshotSaveCommandExecutor(commandSegments);
            case "RESTORE":
                return new SnapshotRestoreLastCommandExecutor(commandSegments);
            default:
                String unsupportedCommandMessage = String.format(UNSUPPORTED_COMMAND_MESSAGE, commandName);
                throw new ResponseStatusException(BAD_REQUEST, unsupportedCommandMessage);
        }
    }
}