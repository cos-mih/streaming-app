package commands;

import entities.Database;

/**
 * Concrete command class representing an "ADD" command. Keeps all information related to said given command.
 */
public class AddStream implements Command {

    private int streamerId;
    private int type;
    private int streamId;
    private int genre;
    private long length;
    private String name;

    public AddStream() {
    }

    public AddStream(int streamerId, int type, int streamId, int genre, long length, String name) {
        this.streamerId = streamerId;
        this.type = type;
        this.streamId = streamId;
        this.genre = genre;
        this.length = length;
        this.name = name;
    }

    @Override
    public void execute(Database db) {
        db.addStream(streamerId, type, streamId, genre, length, name);
    }

    @Override
    public void undo(Database db) {
        db.undoStreamAddition(this.streamerId);
    }
}
