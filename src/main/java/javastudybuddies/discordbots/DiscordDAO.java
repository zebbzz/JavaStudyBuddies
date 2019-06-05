package javastudybuddies.discordbots;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DiscordDAO {
   private static Connection connection;

   static {
       connect("jdbc:postgresql://localhost:5432/JavaStudyBuddies",
               "postgres", "knight8022");
   }

    public static void main(String[] args) {
       DiscordUser user = new DiscordUser();
       user.setName("Test");
       user.setLevel("Beginner");
        insert(user);
    }

    private static boolean connect(String DB_URL, String USER, String PASS)  {
        System.out.println("Hello, sir!");
        connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Done, sir!");

            System.out.println("Opened database successfully");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);

            return false;
        }
    }

    public static <T> int insert(T object)  {
        if (object instanceof DiscordUser)  {
            DiscordUser user = (DiscordUser) object;

            try  {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, level, country, timezone) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLevel());
                preparedStatement.setString(3, user.getCountry());
                preparedStatement.setString(4, user.getTimezone());

                return preparedStatement.executeUpdate();
            }
            catch (Exception e)  {
                e.printStackTrace();
                return -1;
            }
        }

        return -2;
    }
}
