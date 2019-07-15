package javastudybuddies.discordbots.singerbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            Song song = new Song("Lose yourself", "Eminem");
            song.addLine("His palms are sweaty knees weak");
            song.addLine("arms are heavy");
            SingerDAO.insert(song);
            System.out.println(getId(song));

            song.addLine("there's vomit on his sweater already");
            song.addLine("Mom's spaggetti");
            song.addLine("He's nervous");
            song.addLine("But on the surface");
            song.addLine("He looks calm and ready");
            song.addLine("to drop bombs");
            song.addLine("but he keeps on forgetting");
            SingerDAO.updateLines(song);
    }

    public static <T> void updateLines(T object) {
        if (object instanceof Song) {
            Song song = (Song) object;
            try {
                int id = getId(song);  //by song name and author
                if (id <= 0) {
                    insert(song);
                    return;
                }

                PreparedStatement selectLines = connection.prepareStatement("SELECT lyrics FROM songs WHERE " +
                    SingerColumn.NAME.databaseLabel + "='" + song.getName() + "' AND " +
                    SingerColumn.AUTHOR.databaseLabel + "='" + song.getAuthor().getName() + "';");
                ResultSet resultLyrics = selectLines.executeQuery();
                resultLyrics.next();
                String lyrics = resultLyrics.getString(SingerColumn.LYRICS.databaseLabel);
                String[] storedLines = lyrics.split("\n");
               for (String line: storedLines)  {
                   System.out.println("stored: " + line);
               }


                StringBuilder newLines = new StringBuilder();
                int nextNewLine = 0;
                for (int i=0; i<song.getLyrics().size(); i++)  {
                    String songLine = song.getLyrics().get(i);

                    if (songLine.equalsIgnoreCase(storedLines[storedLines.length-1]))  {
                        System.out.println("found " + songLine);
                        nextNewLine = i+1;
                    }

                    if (nextNewLine>0 && i>=nextNewLine)  {
                        newLines.append(songLine + "\n");
                    }
                }

                if (nextNewLine==0)  {
                    return;
                }

                String statement = "UPDATE songs SET (" +
                        SingerColumn.LYRICS.databaseLabel + ") = (?) WHERE " +
                        SingerColumn.NAME.databaseLabel + "='" + song.getName() + "' AND " +
                        SingerColumn.AUTHOR.databaseLabel + "='" + song.getAuthor().getName() + "';";
                System.out.println(statement);
                PreparedStatement storeLines = connection.prepareStatement(statement);
                storeLines.setString(1, lyrics+newLines.toString());

                storeLines.executeUpdate();
            }
            catch (Exception e)  {
                e.printStackTrace();
            }
        }
    }

    public static <T> void insert(T object) {
        if (object instanceof Song) {
            Song song = (Song) object;
            try {
                //Map<SingerColumn, Object> columns = new HashMap<>();
                //columns.put(SingerColumn.NAME, song.getName());
                //columns.put(SingerColumn.AUTHOR, song.getAuthor().getName());

                int id = getId(song);  //by song name and author
                if (id > 0) {
                    return;
                }

                //creating a song if it doesn't exist
                System.out.println("storing song " + song.getName());
                PreparedStatement createSong = connection.prepareStatement("INSERT INTO " +
                        "songs (" + SingerColumn.NAME.databaseLabel + ", " +
                SingerColumn.AUTHOR.databaseLabel + ") VALUES (?, ?);");

                createSong.setObject(1, song.getName());
                createSong.setObject(2, song.getAuthor().getName());
                createSong.executeUpdate();

                PreparedStatement columnsStatement = connection.prepareStatement("SELECT * FROM songs WHERE false;");
                ResultSet resultColumns = columnsStatement.executeQuery();
                //columns.next();

                StringBuilder lyrics = new StringBuilder();
                for (String line: song.getLyrics())  {
                    lyrics.append(line + "\n");
                }

                PreparedStatement updateTable = connection.prepareStatement("UPDATE songs SET (" +
                        SingerColumn.LYRICS.databaseLabel + ") = (?) WHERE " +
                        SingerColumn.NAME.databaseLabel + "='" + song.getName() +
                        "' AND "+ SingerColumn.AUTHOR.databaseLabel + "='" + song.getAuthor().getName() + "';");
                updateTable.setString(1, lyrics.toString());

                int result = updateTable.executeUpdate();



            }
            catch (Exception e)  {
                System.out.println("Something went wrong in insert song method");
                e.printStackTrace();
            }
        }
    }

    public static <T> T getById(int id)  {


        return (T) new Song("", "");
    }

    public static <T> int getId(T object)  {
        Song song;
        if (object instanceof Song)  {
            song = (Song) object;

            try  {
                PreparedStatement selectSong = connection.prepareStatement("SELECT * FROM songs WHERE " +
                        SingerColumn.NAME.databaseLabel + "='" + song.getName() + "' AND " +
                        SingerColumn.AUTHOR.databaseLabel + "='" + song.getAuthor().getName() + "';");
                ResultSet resultSet = selectSong.executeQuery();

                if (resultSet.next())  {
                    return resultSet.getInt("id");
                }

                return 0;
                //song.setLyrics(resultSet.getObject());
            }
            catch (Exception e)  {
                System.out.println("Exception in getId(T object)");
                e.printStackTrace();
                return -2;
            }

        }
        else  {
            return -1;
        }
    }

    //public static int getIdByColumns(Map<SingerColumn, Object> columns)  {
    //    PreparedStatement selectSong = connection.prepareStatement("SELECT * FROM songs WHERE " +
        //            SingerColumn.NAME.databaseLabel + "='" + )
    //}

}
