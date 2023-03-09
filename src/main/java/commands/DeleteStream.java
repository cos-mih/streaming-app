package commands;

import entities.Database;
import entities.Stream;

/**
 * Concrete command class representing a "DELETE" command. Keeps all information related to said given command.
 */
public class DeleteStream implements Command {

    private int streamerId;
    private int streamId;
    private Stream deletedStream = null;

    public DeleteStream(int streamerId, int streamId) {
        this.streamerId = streamerId;
        this.streamId = streamId;
    }

    @Override
    public void execute(Database db) {
        deletedStream = db.getStreams().get(db.findStreamIndex(streamId));
        db.deleteStream(streamerId, streamId);
    }

    @Override
    public void undo(Database db) {
        if (deletedStream == null) {
            return;
        }
        db.undoStreamDeletion(deletedStream, streamerId);
    }
}
