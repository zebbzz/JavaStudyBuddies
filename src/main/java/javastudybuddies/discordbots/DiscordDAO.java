package javastudybuddies.discordbots;



import javastudybuddies.discordbots.singerbot.Song;
import javastudybuddies.discordbots.welcomebot.Column;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiscordDAO {
   private static Connection connection;

   static {
       connect("jdbc:postgresql://localhost:5432/JavaStudyBuddies",
               "postgres", "knight8022");
   }

    public static void main(String[] args) {
       /*DiscordUser user = new DiscordUser();
       user.setName("Test");
       user.setLevel("Advanced");
       user.setAge(24);
       user.setCountry("Brazil");
       user.setTimezone("UTC+3");
        insert(user);*/

       /*DiscordUser user = new DiscordUser();
       user.setName("copperlark2");
       int id = DiscordDAO.getId(user);
       System.out.println("id: " + id);

       DiscordUser user1 = DiscordDAO.getById(DiscordUser.class, 3);
       System.out.println(user1.getName());

       DiscordUser user2 = DiscordDAO.getByUsername(DiscordUser.class, "copperlark2");
       System.out.println("username: " + user2.getName());*/

       /* DiscordUser user3 = DiscordDAO.getByUsername(DiscordUser.class, "copperlark");
        for (String question : user3.getAnswered().keySet()) {
            System.out.println(question + ": " + user3.getAnswered().get(question).getAnswer());
        }*/

        DiscordUser user4 = DiscordDAO.getByUsername(DiscordUser.class, "copperlark ");
        for (String question : user4.getAnswered().keySet()) {
            System.out.println(question + ": " + user4.getAnswered().get(question).getAnswer());
        }
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
                int id = getIdByColumn(object, Column.IDSTRING);
                if (id<=0)  {
                    System.out.println("storing user " + user.getName());
                    PreparedStatement createUser  = connection.prepareStatement("INSERT INTO " +
                            "users ("+ Column.IDSTRING.databaseLabel + ") VALUES (?);");
                    createUser.setString(1, user.getId());

                    createUser.executeUpdate();
                }

                PreparedStatement columnsStatement = connection.prepareStatement("SELECT * FROM users WHERE false;");
                ResultSet columns = columnsStatement.executeQuery();
                //columns.next();

                int result = 0;
                for (int i=1; i<=columns.getMetaData().getColumnCount(); i++)  {
                    String column = columns.getMetaData().getColumnName(i);
                    System.out.println("column: " + column);
                    if (user.getAnswered().keySet().contains(column))  {
                        PreparedStatement updateTable = connection.prepareStatement("UPDATE users SET (" + column +
                                    ") = (?) WHERE " + Column.IDSTRING.databaseLabel + "='" + user.getId() + "';");
                        updateTable.setObject(1, user.getAnswered().get(column).getAnswer());

                        result = updateTable.executeUpdate();
                    }
                }

                System.out.println("STORING TAG: " + user.getTag());
                PreparedStatement updateOther = connection.prepareStatement("UPDATE users " +
                        "SET (" + Column.USERNAME.databaseLabel + ", " +
                        Column.TAGSTRING.databaseLabel +") = " +
                        "(?, ?) WHERE " + Column.IDSTRING.databaseLabel + "='" + user.getId() + "';");
                updateOther.setString(1, user.getName());
                updateOther.setString(2, user.getTag());

                updateOther.executeUpdate();

                return result;
            }
            catch (Exception e)  {
                e.printStackTrace();
                return -1;
            }
        }


        System.out.println("not a discord user");

        return -2;
    }

    public static <T> T getByUsername(Class type, String username)  {
       System.out.println("inside getByUsername: " + username);

       return getById(type, getIdByColumn(username, Column.USERNAME));
    }

    public static <T> T getByIdString(Class type, String idString)  {
       return getById(type, getIdByColumn(idString, Column.IDSTRING));
    }

    public static <T> T getByTagString(Class type, String tagString)  {
       System.out.println("THIS TAG STRING" + tagString);

       boolean isId = false;
       if (tagString.charAt(0)=='<')  {
           isId = true;
           tagString = tagString.substring(1, tagString.length()-1);
       }

       if (tagString.charAt(0)=='@')  {
            System.out.println("@@@");
            tagString = tagString.substring(1);
        }

        if (isId)  {
            return getById(type, getIdByColumn(tagString, Column.IDSTRING));
        }

        return getById(type, getIdByColumn(tagString, Column.TAGSTRING));
    }

    public static <T> T getById(Class type, int id)  {
       System.out.println("Inside getById(" + type + ", " + id + ")");

        if (type == DiscordUser.class)  {
            try  {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users " +
                        "WHERE id=" + id);
                ResultSet result = preparedStatement.executeQuery();
                if (!result.next())  {
                    System.out.println("empty");
                    return null;
                }

                DiscordUser user = new DiscordUser();
                for (int i=1; i<=result.getMetaData().getColumnCount(); i++)  {
                    if (result.getString(i)!=null && !result.getString(i).equalsIgnoreCase(""))  {
                        System.out.println("i: " + i);
                        System.out.println(result.getMetaData().getColumnName(i));
                        System.out.println(result.getString(i));

                        if (Column.getByDatabaseLabel(result.getMetaData().getColumnName(i))!=null) {
                            user.set(Column.getByDatabaseLabel(result.getMetaData().getColumnName(i)).userLabel, result.getString(i));
                        }
                    }
                }
                user.setTag(result.getString(Column.TAGSTRING.databaseLabel));

               /* user.setName(result.getString("username"));
                user.setTimezone(result.getString("timezone"));
                user.setLevel(result.getString("level"));
                user.setAge(result.getInt("age"));
                user.setCountry(result.getString("country"));*/

                return (T) user;
            } catch (Exception e)  {
                    e.printStackTrace();
                    return null;
            }
        }
        else  {
            return null;
        }
    }

    public static <T> int getIdByColumn(T object, Column column)  {
            Object value;

            if (object instanceof DiscordUser) {
                value = ((DiscordUser) object).get(column.userLabel);
            }
            else if (object instanceof String) {
                value = (String) object;
            }
            else {
                return 0;
            }

           try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users " +
                        "WHERE " + column.databaseLabel + "='" + value + "' ORDER BY id ASC");
                System.out.println("frodo databaselevel: " + column.databaseLabel);
                ResultSet result = preparedStatement.executeQuery();
                int objectId;
                if (result.next()) {
                    objectId = result.getInt("id");
                } else {
                    objectId = 0;
                }

                return objectId;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
    }
}

