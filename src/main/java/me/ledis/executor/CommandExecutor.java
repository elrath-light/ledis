package me.ledis.executor;

public abstract class CommandExecutor {
    private String[] commandSegments;

    protected CommandExecutor(String[] commandSegments) {
        this.commandSegments = commandSegments;
        validateSyntax();
    }

    public abstract String execute();
    public abstract void validateSyntax();

    protected String[] getCommandSegments() {
        return commandSegments;
    }

}
