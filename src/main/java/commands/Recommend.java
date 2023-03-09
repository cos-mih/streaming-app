package commands;

import entities.Database;

/**
 * Concrete command class representing a "RECOMMEND" or "SURPRISE" command. Keeps all information related to said given command.
 */
public class Recommend implements Command {

    private int userId;
    private String type;
    private String recType;

    public Recommend(int userId, String type, String recType) {
        this.userId = userId;
        this.type = type;
        this.recType = recType;
    }

    @Override
    public void execute(Database db) {
        db.makeRecommendation(userId, type, recType);
    }

    @Override
    public void undo(Database db) {
        System.out.println("Operation cannot be undone");
    }
}
