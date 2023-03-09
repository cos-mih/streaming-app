package interpreters;

import commands.Command;
import commands.ListenToStream;

/**
 * Concrete class representing a interpreter for the "LISTEN" functionality.
 */
public class ListenCommandExtractor implements CommandExtractor{
    @Override
    public boolean appliesFor(String[] arguments) {
        return "LISTEN".equals(arguments[1]);
    }

    @Override
    public Command createCommandObj(String[] arguments) {
        int id = Integer.parseInt(arguments[0]);
        int streamToListenId = Integer.parseInt(arguments[2]);
        Command listen = new ListenToStream(id, streamToListenId);
        return listen;
    }
}
