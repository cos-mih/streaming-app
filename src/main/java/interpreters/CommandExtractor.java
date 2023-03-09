package interpreters;

import commands.Command;

/**
 * Interface used to define a command interpreter based on a line read from commands.txt
 */
public interface CommandExtractor {

    /**
     * Checks whether the current intepreter is appropriate for a given command read from a file.
     * @param arguments string array representing a line read from the commands.txt file
     * @return true if applicable; false if not
     */
    public boolean appliesFor(String[] arguments);

    /**
     * Implements the actual functionality of the CommandExtractor - it interprets the arguments given and creates a
     * Command object to further process it.
     * @param arguments string array representing a line read from the commands.txt file
     * @return Command object corresponding to the type of command that was interpreted
     */
    public Command createCommandObj(String[] arguments);
}
