package commands;

import entities.Database;

/**
 * Concrete command class representing a "LIST" command. Keeps all information related to said given command.
 */
public class ListStreams implements Command {

    private int id;

    public ListStreams(int id) {
        this.id = id;
    }

    @Override
    public void execute(Database db) {
        db.listStreams(this.id);
    }

    @Override
    public void undo(Database db) {
        System.out.println("Operation cannot be undone");
    }
}
