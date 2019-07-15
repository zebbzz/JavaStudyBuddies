package javastudybuddies.discordbots.entities;

public enum Column {
    NAME("name", "name", "projects"),
    DESCRIPTION("description", "description",  "projects"),
    STATUS("status", "status", "projects"),
    COMPLETED("completed", "completed", "projects"),
    DIFFICULTY("difficulty", "difficulty", "projects"),

    USERNAME("username", "username", "users"), LEVEL("level", "level", "users"),
    COUNTRY("country", "country", "users"), TIMEZONE("timezone", "timezone", "users"),
    AGE("age", "age", "users"), IDSTRING("id_string", "id", "users"), TAGSTRING("tag_string", "tag", "users"),
    GOAL("goal", "goal", "users"), TECH("tech", "tech", "users");

    public final String databaseLabel;
    public final String userLabel;
    public final String table;

    private Column(String databaseLabel, String userLabel, String table)  {
        this.databaseLabel = databaseLabel;
        this.userLabel = userLabel;
        this.table = table;
    }

    public static Column getByDatabaseLabel(String label)  {
        for (Column e : values()) {
            if (e.databaseLabel.equals(label)) {
                return e;
            }
        }

        return null;
    }
}
