package me.ledis.executor;

import me.ledis.data.LedisData;

import static me.ledis.constant.ResponseMessage.OK_MESSAGE;

public class SnapshotSaveCommandExecutor extends CommandExecutor {
    public SnapshotSaveCommandExecutor(String[] commandSegments) {
        super(commandSegments);
    }

    @Override
    public String execute() {
        LedisData.saveCurrentState();
        return OK_MESSAGE;
    }
}
