package me.ledis.executor;

import me.ledis.data.LedisData;

import static me.ledis.constant.ResponseMessage.OK_MESSAGE;

public class SnapshotRestoreLastCommandExecutor extends CommandExecutor {
    public SnapshotRestoreLastCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        LedisData.loadLastSavedSnapshot();
        return OK_MESSAGE;
    }
}
