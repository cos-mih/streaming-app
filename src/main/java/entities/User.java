package entities;

import java.util.List;

/**
 * Concrete class representing a user in the system.
 */
public class User extends GeneralUser {


    public User(int id, String name, List<Stream> streams) {
        this.id = id;
        this.name = name;
        this.streams = streams;
    }

    /**
     * Method to delete a given stream from the stream collection of the current user.
     * @param stream
     */
    public void deleteStream(Stream stream) {
        this.streams.remove(stream);
    }


    /**
     * Method to indicate whether a stream was already listened to by the current user.
     * @param stream stream to be checked
     * @return true if stream is in the collection of streams listened to by the user
     */
    public boolean listenedTo(Stream stream) {
        for (Stream s: this.streams) {
            if (s.getId() == stream.getId()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method to indicate whether a streamer was already listened to by the current user.
     * @param streamer streamer to be checked
     * @return true if streamer is associated with any stream in the collection of streams listened to by the user
     */
    public boolean listenedToStreamer(Streamer streamer) {
        for (Stream s: this.streams) {
            if (s.getStreamer().equals(streamer)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object u) {
        return this.id == ((User)u).id;
    }
}
