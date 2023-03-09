package entities;

/**
 * Concrete class representing a streamer in the system.
 */
public class Streamer extends GeneralUser {

    private StreamerType type;

    public Streamer.StreamerType getType() {
        return type;
    }

    /**
     * Inner enum class defining the possible types of streamer.
     */
    public enum StreamerType {
        MUSICIAN("musician"),
        PODCASTER("podcaster"),
        AUTHOR("author");

        private String string;

        StreamerType(String string) {
            this.string = string;
        }
    }

    public Streamer(int id, String name, int type) {
        super(id, name);
        switch (type) {
            case 1:
                this.type = StreamerType.MUSICIAN;
                break;
            case 2:
                this.type = StreamerType.PODCASTER;
                break;
            case 3:
                this.type = StreamerType.AUTHOR;
                break;
        }
    }

    /**
     * Method to attach a new stream to the stream collection of the current streamer.
     * @param stream stream object to be added
     */
    public void attachStream(Stream stream) {
        this.streams.add(stream);
    }

    public boolean equals(Object s) {
        return this.id == ((Streamer)s).id;
    }
}
