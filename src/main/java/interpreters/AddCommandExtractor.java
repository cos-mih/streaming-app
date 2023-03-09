package interpreters;

import commands.AddStream;
import commands.Command;

/**
 * Concrete class representing a interpreter for the "ADD" functionality.
 */
public class AddCommandExtractor implements CommandExtractor{
    @Override
    public boolean appliesFor(String[] arguments) {
        return "ADD".equals(arguments[1]);
    }

    @Override
    public Command createCommandObj(String[] arguments) {
        int id = Integer.parseInt(arguments[0]);
        int type = Integer.parseInt(arguments[2]);
        int newStreamId = Integer.parseInt(arguments[3]);
        int genre = Integer.parseInt(arguments[4]);
        long length = Long.parseLong(arguments[5]);
        String name = arguments[6];
        for (int i = 7; i < arguments.length; i++) {
            name += " " + arguments[i];
        }
        Command add = new AddStream(id, type, newStreamId, genre, length, name);
        return add;
    }
}
