package presenter;

public interface Command {
	/**
	 * doCommand method executes a command
	 * @param args is list of commands
	 */
	void doCommand(String[] args);
}