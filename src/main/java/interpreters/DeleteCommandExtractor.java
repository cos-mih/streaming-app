package interpreters;

import commands.Command;
import commands.DeleteStream;

/**
 * Concrete class representing a interpreter for the "DELETE" functionality.
 */
public class DeleteCommandExtractor implements CommandExtractor{
    @Override
    public boolean appliesFor(String[] arguments) {
        return "DELETE".equals(arguments[1]);
    }

    @Override
    public Command createCommandObj(String[] arguments) {
        int id = Integer.parseInt(arguments[0]);
        int streamId = Integer.parseInt(arguments[2]);
        Command delete = new DeleteStream(id, streamId);
        return delete;
    }
}
