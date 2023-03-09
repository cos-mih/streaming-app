package entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing methods for reading data from the given CSV type files and transporting them into the corresponding
 * data structures for easier manipulation during the running of the application.
 */
public class CSVDataGetter {

    /**
     * Directory of the input files.
     */
    private final String directory = "src/main/resources/";

    public CSVDataGetter() {

    }

    /**
     * Method that fills a Database object with all the initial information found in the input CSV files.
     * @param db Database object to populate
     * @param streamerFile name of streamers file
     * @param streamFile name of streams data file
     * @param userFile name of users data file
     */
    public void populate(Database db, String streamerFile, String streamFile, String userFile) {
        List<String[]> streamersData = this.extractData(streamerFile);
        List<String[]> streamsData = this.extractData(streamFile);
        List<String[]> usersData = this.extractData(userFile);

        this.populateStreamers(db, streamersData);
        this.populateStreams(db, streamsData);
        this.populateUsers(db, usersData);
    }

    /**
     * Method that reads all data from a given CSV type file.
     * @param filename name of file to be read
     * @return List of String arrays, each element of the list representing a line read
     */
    public List<String[]> extractData(String filename) {
        List<String[]> data =  null;
        try (CSVReader csvr = new CSVReaderBuilder(new FileReader(directory + filename)).withSkipLines(1).build()) {
            data = csvr.readAll();
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (CsvException e) {
            System.err.println("Read error: " + e.getMessage());
        }

        return data;
    }

    /**
     * Method to create Streamer objects as dictated by the input file and add them intro the database.
     * @param db Database object to populate
     * @param streamersData data read from the corresponding CSV file
     */
    public void populateStreamers(Database db, List<String[]> streamersData) {
        for (String[] entry: streamersData) {
            int type = Integer.parseInt(entry[0]);
            int id = Integer.parseInt(entry[1]);
            String name = entry[2];

            Streamer streamer = new Streamer(id, name, type);
            db.getStreamers().add(streamer);
        }
    }

    /**
     * Method to create Stream objects as dictated by the input file and add them intro the database.
     * @param db Database object to populate
     * @param streamsData data read from the corresponding CSV file
     */
    public void populateStreams(Database db, List<String[]> streamsData) {
        for (String[] entry: streamsData) {
            int type = Integer.parseInt(entry[0]);
            int id = Integer.parseInt(entry[1]);
            int genre = Integer.parseInt(entry[2]);
            long noOfStreams = Long.parseLong(entry[3]);
            int streamerId = Integer.parseInt(entry[4]);
            long length = Long.parseLong(entry[5]);
            long dateAdded = Long.parseLong(entry[6]);
            String name = entry[7];

            Streamer streamer = db.getStreamers().get(db.findStreamerIndex(streamerId));
            Stream.StreamBuilder streamBuilder = db.createStreamBuilderObject(type);
            Stream stream = streamBuilder.withId(id).withNoOfStreams(noOfStreams).withStreamer(streamer)
                        .withLength(length).withDateAdded(dateAdded).withName(name).withGenre(genre).build();
            db.getStreams().add(stream);
            streamer.attachStream(stream);
        }
    }

    /**
     * Method to create User objects as dictated by the input file and add them intro the database.
     * @param db Database object to populate
     * @param usersData data read from the corresponding CSV file
     */
    public void populateUsers(Database db, List<String[]> usersData) {
        for (String[] entry: usersData) {
            int id = Integer.parseInt(entry[0]);
            String name = entry[1];
            String[] streamIds = entry[2].split(" ");

            List<Stream> streams = new ArrayList<Stream>();
            for (String streamId: streamIds) {
                if (db.findStreamIndex(Integer.parseInt(streamId)) >= 0) {
                    streams.add(db.getStreams().get(db.findStreamIndex(Integer.parseInt(streamId))));
                }
            }

            User user = new User(id, name, streams);
            db.getUsers().add(user);
        }
    }
}
