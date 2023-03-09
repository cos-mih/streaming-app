package commands;

import entities.Database;

/**
 * Interface modelling a command given in the system, with both execute and undo functionalities.
 */
public interface Command {

    public void execute(Database db);
    public void undo(Database db);
}
