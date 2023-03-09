package commands;

import commands.Command;
import entities.Database;

/**
 * Concrete command class representing a "LISTEN" command. Keeps all information related to said given command.
 */
public class ListenToStream implements Command {

    private int userId;
    private int streamId;

    public ListenToStream(int userId, int streamId) {
        this.userId = userId;
        this.streamId = streamId;
    }

    @Override
    public void execute(Database db) {
        db.listen(userId, streamId);
    }

    @Override
    public void undo(Database db) {
        System.out.println("Operation cannot be undone");
    }
}
