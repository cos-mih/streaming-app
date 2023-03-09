package entities;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * Abstract class representing the general characteristics of a stream entity in the system.
 */
public abstract class Stream {

    private int id;
    private long noOfStreams;
    private Streamer streamer;
    private long length;
    private long dateAdded;
    private String name;

    public int getId() {
        return id;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public long getLength() {
        return length;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    public void incrementListens() {
        this.noOfStreams++;
    }

    public Stream() {

    }

    /**
     * Method to convert a Stream type object into a JSON object.
     * @return JSON object corresponding to current stream object
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", Integer.toString(this.id));
        json.put("name", this.name);
        json.put("streamerName", this.streamer.getName());
        json.put("noOfListenings", Long.toString(this.noOfStreams));
        json.put("length", this.getLengthString());
        json.put("dateAdded", this.getDateString());

        return json;
    }

    /**
     * Method to convert the dateAdded attribute of a stream into a dd-MM-yyyy format string.
     * @return string representing the dateAdded in a human-readable format
     */
    public String getDateString() {
        Date date = new Date((long)(1000 * this.dateAdded));
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dateAdded = format.format(date);
        return dateAdded;
    }

    /**
     * Method to convert the length attribute of a stream to a string in hh:mm:ss format.
     * @return string representing the length of a stream in a human-readable format
     */
    public String getLengthString() {
        Duration dr = Duration.ofSeconds(this.length);
        String length = "";
        if (dr.toHours() > 0) {
            if (dr.toHours() < 10) {
                length += "0";
            }
            length += Long.toString(dr.toHours());
            length += ":";
        }

        dr = dr.minusHours(dr.toHours());
        if (dr.toMinutes() < 10) {
            length += "0";
        }
        length += Long.toString(dr.toMinutes());
        length += ":";

        dr = dr.minusMinutes(dr.toMinutes());
        if (dr.toSeconds() < 10) {
            length += "0";
        }
        length += Long.toString(dr.toSeconds());
        return length;
    }

    /**
     * Method that creates a Comparator object to compare stream objects based only on the number of streams.
     * @return comparator
     */
    public static Comparator<Stream> getNoOfStreamsComparator() {
        Comparator<Stream> comp = new Comparator<Stream>() {
            @Override
            public int compare(Stream o1, Stream o2) {
                if (o1.getNoOfStreams() < o2.getNoOfStreams()) {
                    return -1;
                }
                if (o1.getNoOfStreams() == o2.getNoOfStreams()) {
                    return 0;
                }
                return 1;
            }
        };
        return comp;
    }

    /**
     * Method that creates a Comparator object to compare stream objects based on the dateAdded attribute, and in case
     * of equality, based on the number of streams.
     * @return comparator
     */
    public static Comparator<Stream> getDateAndNoOfStreamsComparator() {
        Comparator<Stream> comp = new Comparator<Stream>() {
            @Override
            public int compare(Stream o1, Stream o2) {
                if (o2.getDateString().equals(o1.getDateString())) {
                    if (o1.getNoOfStreams() > o2.getNoOfStreams()) {
                        return -1;
                    }
                    if (o1.getNoOfStreams() == o2.getNoOfStreams()) {
                        return 0;
                    }
                    return 1;
                }
                return new Date(o2.getDateAdded()).compareTo(new Date(o1.getDateAdded()));
            }
        };
        return comp;
    }


    /**
     * Abstract Builder class for creating Stream type objects; to be inherited by concrete builders in subclasses of Stream.
     * @param <T> the concrete Builder of a subclass of Stream
     */
    public static abstract class StreamBuilder<T extends StreamBuilder<T>> {
        protected Stream stream;

        public abstract T getThis();

        public T withId(int id) {
            this.stream.id = id;
            return getThis();
        }

        public T withNoOfStreams(long noOfStreams) {
            this.stream.noOfStreams = noOfStreams;
            return getThis();
        }

        public T withStreamer(Streamer streamer) {
            this.stream.streamer = streamer;
            return getThis();
        }

        public T withLength(long length) {
            this.stream.length = length;
            return getThis();
        }

        public T withDateAdded(long date) {
            this.stream.dateAdded = date;
            return getThis();
        }

        public T withName(String name) {
            this.stream.name = name;
            return getThis();
        }

        public abstract T withGenre(int genre);
        public Stream build() {
            return this.stream;
        }
    }


    @Override
    public boolean equals(Object obj) {
        return this.id == ((Stream)obj).id;
    }
}
