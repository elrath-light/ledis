package me.ledis.executor;

public abstract class CommandExecutor {
    private String[] commandSegments;

    protected CommandExecutor(String[] commandSegments) {
        this.commandSegments = commandSegments;
    }

    public abstract String execute();

    protected String[] getCommandSegments() {
        return commandSegments;
    }

}
