package javastudybuddies.discordbots.singerbot;

public enum SingerColumn {
    NAME("name", "name"), AUTHOR("author", "author"), LYRICS("lyrics", "lyrics");

    public final String databaseLabel;
    public final String userLabel;

    private SingerColumn(String databaseLabel, String userLabel)  {
        this.databaseLabel = databaseLabel;
        this.userLabel = userLabel;
    }

    public static SingerColumn getByDatabaseLabel(String label)  {
        for (SingerColumn e : values()) {
            if (e.databaseLabel.equals(label)) {
                return e;
            }
        }

        return null;
    }

}
