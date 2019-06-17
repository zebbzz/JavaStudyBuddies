package javastudybuddies.discordbots.singerbot;

import javastudybuddies.discordbots.welcomebot.Column;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingerDAO {
    private static Connection connection;

    static {
        connect("jdbc:postgresql://localhost:5432/JavaStudyBuddies",
                "postgres", "knight8022");
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

    public static void main(String[] args) {

    }

    /*public <T> void insert(T object) {
        if (object instanceof Song) {
            Song song = (Song) object;
            try {
                Map<SingerColumn, Object> columns = new HashMap<>();
                columns.put(SingerColumn.NAME, song.getName());
                columns.put(SingerColumn.AUTHOR, song.getAuthor().getName());

                int id = getIdByColumns(columns);  //by song name and author
                if (id <= 0) {  //creating a song if it doesn't exist
                    System.out.println("storing song " + song.getName());
                    PreparedStatement createSong = connection.prepareStatement("INSERT INTO " +
                            "songs (" + SingerColumn.NAME.databaseLabel + ", " +
                    SingerColumn.AUTHOR.databaseLabel + ") VALUES (?, ?);");

                    createSong.setObject(1, song.getName());
                    createSong.setObject(2, song.getAuthor().getName());
                    createSong.executeUpdate();
                }

                PreparedStatement columnsStatement = connection.prepareStatement("SELECT * FROM songs WHERE false;");
                ResultSet resultColumns = columnsStatement.executeQuery();
                //columns.next();

                int result = 0;
                for (int i = 1; i <= resultColumns.getMetaData().getColumnCount(); i++) {
                    String column = resultColumns.getMetaData().getColumnName(i);
                    System.out.println("column: " + column);
                    if (song.getAnswered().keySet().contains(column)) {
                        PreparedStatement updateTable = connection.prepareStatement("UPDATE users SET (" + column +
                                ") = (?) WHERE " + Column.IDSTRING.databaseLabel + "='" + song.getId() + "';");
                        updateTable.setObject(1, song.getAnswered().get(column).getAnswer());

                        result = updateTable.executeUpdate();
                    }
                }

            }
            catch (Exception e)  {

            }
        }
    }*/

    public <T> T getById(int id)  {
        return (T) new Song();
    }

    public int getIdByColumns(Map<SingerColumn, Object> columns)  {
        return -1;
    }

}
