import commands.*;
import entities.*;
import interpreters.*;

import java.io.*;
import java.util.List;

public class ProiectPOO {
    public static List<CommandExtractor> commandExtractors;

    /**
     * Main logic of the application.
     * @param args streamer.csv, stream.csv, user.csv, in this order
     */
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Nothing to read here");
            return;
        }

        /* Initialising a database from the given CSV files */
        Database db = Database.getInstance();
        new CSVDataGetter().populate(db, args[0], args[1], args[2]);

        /* Reading and processing commands line by line */
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + args[3]))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] arguments = line.split(" ");
                Command command = selectCommand(arguments).createCommandObj(arguments);
                command.execute(db);
            }
        } catch (IOException e) {
            System.err.println("Commands file error: " + e.getMessage());
        }

        /* Resets database instance to null (for testing purposes) */
        Database.resetInstance();
    }

    /**
     * Method that searches for the appropriate CommandExtractor to process a new command based on the command's keyword.
     * @param arguments string array representing a line read from the commands.txt file
     * @return a CommandExtractor that can appropriately interpret the current array of arguments
     */
    private static CommandExtractor selectCommand(String[] arguments) {
        initializeExtractors();

        for (CommandExtractor commandExtractor: commandExtractors) {
            if (commandExtractor.appliesFor(arguments)) {
                return commandExtractor;
            }
        }

        throw new IllegalArgumentException("Command not suported");
    }

    /* Creates a list of all possible concrete CommandExtractors to choose from for each command */
    private static void initializeExtractors() {
        commandExtractors = List.of(new ListCommandExtractor(), new AddCommandExtractor(), new DeleteCommandExtractor(),
                new ListenCommandExtractor(), new RecommendCommandExtractor());
    }
}
