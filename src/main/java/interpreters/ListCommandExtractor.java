package interpreters;

import commands.Command;
import commands.ListStreams;

/**
 * Concrete class representing a interpreter for the "LIST" functionality.
 */
public class ListCommandExtractor implements CommandExtractor{
    @Override
    public boolean appliesFor(String[] arguments) {
        return "LIST".equals(arguments[1]);
    }

    @Override
    public Command createCommandObj(String[] arguments) {
        int id = Integer.parseInt(arguments[0]);
        Command list = new ListStreams(id);
        return list;
    }
}
