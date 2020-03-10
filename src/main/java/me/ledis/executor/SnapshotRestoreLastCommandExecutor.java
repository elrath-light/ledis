package me.ledis.executor;

import me.ledis.data.LedisData;
import org.springframework.web.server.ResponseStatusException;

import static me.ledis.constant.ResponseMessage.OK_MESSAGE;
import static me.ledis.constant.ResponseMessage.RESTORE_SYNTAX_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SnapshotRestoreLastCommandExecutor extends CommandExecutor {
    public SnapshotRestoreLastCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        LedisData.loadLastSavedSnapshot();
        return OK_MESSAGE;
    }

    @Override
    public void validateSyntax() {
        if (getCommandSegments().length != 1) {
            throw new ResponseStatusException(BAD_REQUEST, RESTORE_SYNTAX_ERROR_MESSAGE);
        }
    }
}
