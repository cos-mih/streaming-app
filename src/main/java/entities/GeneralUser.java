package entities;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class modelling the general functionality attached to an acting entity in the system: user or streamer.
 */
public abstract class GeneralUser {

    protected int id;
    protected String name;
    protected List<Stream> streams;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public GeneralUser() {

    }

    public GeneralUser(int id, String name) {
        this.id = id;
        this.name = name;
        this.streams = new ArrayList<Stream>();
    }

    /**
     * Method to list all streams in the stream collection of a user or streamer in JSON format.
     */
    public void listStreams() {
        JSONArray json = new JSONArray();
        for (Stream s: this.streams) {
            json.put(s.toJSON());
        }

        System.out.println(json.toString());
    }
}
