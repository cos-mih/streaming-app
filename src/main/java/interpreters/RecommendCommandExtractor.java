package interpreters;

import commands.Command;
import commands.Recommend;

import java.util.List;

/**
 * Concrete class representing a interpreter for the "RECOMMEND and "SURPRISE" functionalities.
 */
public class RecommendCommandExtractor implements CommandExtractor{
    @Override
    public boolean appliesFor(String[] arguments) {
        return List.of("RECOMMEND", "SURPRISE").contains(arguments[1]);
    }

    @Override
    public Command createCommandObj(String[] arguments) {
        int id = Integer.parseInt(arguments[0]);
        String recType = arguments[1];
        String streamType = arguments[2];
        Command basicRec = new Recommend(id, streamType, recType);
        return basicRec;
    }
}
