package entities;

import org.json.JSONArray;

import java.time.Instant;
import java.util.*;

/**
 * Singleton class representing an abstract database containing all information related to the application (Lists of
 * streamers, streams and users.
 */
public class Database {

    private static Database database = null;
    private ArrayList<User> users;
    private ArrayList<Streamer> streamers;
    private ArrayList<Stream> streams;

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Streamer> getStreamers() {
        return streamers;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    private Database() {
        this.users = new ArrayList<User>();
        this.streamers = new ArrayList<Streamer>();
        this.streams = new ArrayList<Stream>();
    }

    /**
     * Method to get the single existing instance of the database.
     * @return database instance
     */
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    /**
     * Resets the database instance to null for testing purposes (old information needs to be discarded).
     */
    public static void resetInstance() {
        database = null;
    }

    /**
     * Method to find the index of a Streamer object identified by its id in the ArrayList of the database
     * @param id id of streamer
     * @return index of streamer with id in the streamers ArrayList
     */
    public int findStreamerIndex(int id) {
        return this.streamers.indexOf(new Streamer(id, null, 0));
    }

    /**
     * Method to find the index of a Stream object identified by its id in the ArrayList of the database
     * @param id id of stream
     * @return index of stream with id in the streams ArrayList
     */
    public int findStreamIndex(int id) {
        for (Stream s: this.streams) {
            if (s.getId() == id) {
                return this.streams.indexOf(s);
            }
        }

        return -1;
    }

    /**
     * Method to find the index of a User object identified by its id in the ArrayList of the database
     * @param id id of user
     * @return index of user with id in the users ArrayList
     */
    public int findUserIndex(int id) {
        return this.users.indexOf(new User(id, null, null));
    }

    /**
     * Method to find the entity with a given id.
     * @param id id of entity (User or Streamer)
     * @return Streamer or User object corresponding to id
     */
    public GeneralUser findId(int id) {
        if (findStreamerIndex(id) >= 0) {
            return this.streamers.get(findStreamerIndex(id));
        }
        if (findUserIndex(id) >= 0) {
            return this.users.get(findUserIndex(id));
        }

        return null;
    }

    /**
     * Method that creates the appropriate subclass of StreamBuilder based on the type provided.
     * @param type tipe of builder needed
     * @return SongBuilder, PodcastBuilder or AudiobookBuilder based on type
     */
    public Stream.StreamBuilder createStreamBuilderObject(int type) {

        switch (type) {
            case 1:
                return new Song.SongBuilder();
            case 2:
                return new Podcast.PodcastBuilder();
            case 3:
                return new Audiobook.AudiobookBuilder();
        }

        return null;
    }

    /**
     * Method to trigger the listing of the streams corresponding to an entity identified by an id (Streamer or User)
     * @param id id of entity that has to list streams
     */
    public void listStreams(int id) {
        GeneralUser entity = this.findId(id);
        if (entity != null) {
            entity.listStreams();
        }
    }

    /**
     * Method to create and add a new Stream object to both the streams list and the list of streams of the streamer creating it.
     * @param streamerId id o streamer creating the new stream
     * @param type type of stream
     * @param streamId id for the new stream
     * @param genre genre of new stream
     * @param length length of new stream
     * @param name name of new stream
     */
    public void addStream(int streamerId, int type, int streamId, int genre, long length, String name) {
        Streamer streamer = (Streamer)this.findId(streamerId);
        long date = Instant.now().getEpochSecond();
        Stream.StreamBuilder streamBuilder = createStreamBuilderObject(type);
        Stream stream = streamBuilder.withId(streamId).withNoOfStreams(0).withStreamer(streamer)
                .withLength(length).withDateAdded(date).withName(name).withGenre(genre).build();
        this.streams.add(stream);
        ((Streamer) streamer).getStreams().add(stream);
    }

    /**
     * Deletes a newly added stream.
     * @param streamerId id of streamer that wants to undo the addition of a new stream.
     */
    public void undoStreamAddition(int streamerId) {
        deleteStream(streamerId, this.streams.get(this.streams.size() - 1).getId());
    }

    /**
     * Method to delete a stream identified by its id from the database: streams list, stream list of streamer and stream
     * lists of users.
     * @param streamerId id of streamer who deletes the stream
     * @param streamId id of stream to be deleted
     */
    public void deleteStream(int streamerId, int streamId) {
        Stream stream = this.streams.get(findStreamIndex(streamId));
        Streamer streamer = this.streamers.get(this.findStreamerIndex(streamerId));
        if (!streamer.streams.contains(stream)) {
            return;
        }

        streamer.getStreams().remove(stream);
        this.streams.remove(stream);

        for (User user: this.users) {
            user.deleteStream(stream);
        }
    }

    /**
     * Method to undo stream deletion.
     * @param stream previously deleted stream
     * @param streamerId id of corresponding streamer
     */
    public void undoStreamDeletion(Stream stream, int streamerId) {
        this.streamers.get(this.findStreamerIndex(streamerId)).attachStream(stream);
        this.streams.add(stream);
    }

    /**
     * Method to update database after a user listens to a stream: it is added to its stream collection and its noOfStreams
     * attribute is incremented.
     * @param userId id of user listening to stream
     * @param streamId id of stream being listened to
     */
    public void listen(int userId, int streamId) {
        Stream streamToListenTo = this.streams.get(findStreamIndex(streamId));
        User user = this.users.get(findUserIndex(userId));

        streamToListenTo.incrementListens();
        user.getStreams().add(streamToListenTo);
    }

    /**
     * Method to trigger the recommendation process (both basic and surprise).
     * @param userId id of user requesting a recommendation
     * @param type type of stream to recommend
     * @param recType type of recommendation
     */
    public void makeRecommendation(int userId, String type, String recType) {
        User user = this.users.get(findUserIndex(userId));
        generateRecs(user, type, recType);
    }


    /**
     * Method that call the recommendation generator based on the stream type needed.
     * @param user user requesting recommendation
     * @param type type of stream to be recommended
     * @param recType type of recommendation
     */
    public void generateRecs(User user, String type, String recType) {
        switch (type) {
            case "SONG":
                chooseRecType(user, recType, Streamer.StreamerType.MUSICIAN);
                break;
            case "PODCAST":
                chooseRecType(user, recType, Streamer.StreamerType.PODCASTER);
                break;
            case "AUDIOBOOK":
                chooseRecType(user, recType, Streamer.StreamerType.AUTHOR);
                break;
        }
    }

    /**
     * Method to generate the appropriate list of possible streams to be recommended based on the type of recommendation
     * (basic or surprise) and then print the target number from the top of the list.
     * @param user user requestion recommendation
     * @param recType type of recommendation to be made
     * @param type StreamerType enum value indicating the type of stream to be recommended
     */
    public void chooseRecType(User user, String recType, Streamer.StreamerType type) {
        List<Stream> recs = null;
        switch (recType) {
            case "RECOMMEND":
                recs = this.getBasicArray(user, type);
                this.printArray(recs, 5);
                break;
            case "SURPRISE":
                recs = this.getSurpriseArray(user, type);
                this.printArray(recs, 3);
        }
    }

    /**
     * Method to generate a list of streams for a given user: all streams from known streamers the user has not yet
     * listened to.
     * @param user user requesting recommendation
     * @param type StreamerType enum value indicating the type of stream to be recommended
     * @return list of streams eligible for recommending, sorted by number of listenings
     */
    public List<Stream> getBasicArray(User user, Streamer.StreamerType type) {
        ArrayList<Stream> recs = new ArrayList<Stream>();
        for (Streamer streamer: this.streamers) {
            if (streamer.getType() == type && user.listenedToStreamer(streamer)) {
                for (Stream stream: streamer.getStreams()) {
                    if (!user.listenedTo(stream)) {
                        recs.add(stream);
                    }
                }
            }
        }
        recs.sort(Stream.getNoOfStreamsComparator());
        return recs;
    }

    /**
     * Method to generate a list of streams for a given user: all streams from unknown streamers the user has not yet
     * listened to.
     * @param user user requesting recommendation
     * @param type StreamerType enum value indicating the type of stream to be recommended
     * @return list of streams eligible for recommending, sorted by number of listenings
     */
    public List<Stream> getSurpriseArray(User user, Streamer.StreamerType type) {
        ArrayList<Stream> recs = new ArrayList<Stream>();
        for (Streamer streamer: this.streamers) {
            if (streamer.getType() == type && !user.listenedToStreamer(streamer)) {
                for (Stream stream: streamer.getStreams()) {
                    recs.add(stream);
                }
            }
        }
        recs.sort(Stream.getDateAndNoOfStreamsComparator());
        return recs;
    }

    /**
     * Method used to print a given list of recommended streams in json format.
     * @param recStreams list of recommended streams
     * @param n number of streams to be printed
     */
    public void printArray(List<Stream> recStreams, int n) {
        JSONArray json = new JSONArray();
        for (int i =0; i < n && i < recStreams.size(); i++) {
            json.put(recStreams.get(i).toJSON());
        }
        System.out.println(json);
    }
}
