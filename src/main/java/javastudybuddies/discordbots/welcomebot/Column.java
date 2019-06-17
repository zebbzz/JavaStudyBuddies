package javastudybuddies.discordbots.welcomebot;

public enum Column {
       USERNAME("username", "username"), LEVEL("level", "level"),
       COUNTRY("country", "country"), TIMEZONE("timezone", "timezone"),
       AGE("age", "age"), IDSTRING("id_string", "id"), TAGSTRING("tag_string", "tag"),
       GOAL("goal", "goal"), TECH("tech", "tech");

       public final String databaseLabel;
       public final String userLabel;

       private Column(String databaseLabel, String userLabel)  {
              this.databaseLabel = databaseLabel;
              this.userLabel = userLabel;
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
