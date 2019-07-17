package javastudybuddies.discordbots;



import javastudybuddies.discordbots.entities.Column;
import javastudybuddies.discordbots.entities.DiscordUser;
import javastudybuddies.discordbots.entities.Insertable;
import javastudybuddies.discordbots.projectsbot.entities.Project;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiscordDAO {
   private static Connection connection;

   static {
       try {
           //Path path = Paths.get("src", "main", "resources", "setups", "database.login");
           InputStream dbIs = DiscordDAO.class.getResourceAsStream("/setups/database.login");
           //System.out.println("path: " + dbFile);
           BufferedReader br = new BufferedReader(
                    new InputStreamReader(DiscordDAO.class.getResourceAsStream("/setups/database.login")));
           String login = br.readLine();
           br.close();

           System.out.println("login: " + login);
           connect(login, "postgres", "knight8022");
       }
       catch (Exception e)  {
           e.printStackTrace();
           System.err.println("COULD NOT CONNECT TO A DATABASE");
       }
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

       DiscordUser user2 = DiscordDAO.getByName(DiscordUser.class, "copperlark2");
       System.out.println("username: " + user2.getName());*/

       /* DiscordUser user3 = DiscordDAO.getByName(DiscordUser.class, "copperlark");
        for (String question : user3.getAnswered().keySet()) {
            System.out.println(question + ": " + user3.getAnswered().get(question).getAnswer());
        }*/

      /*DiscordUser user4 = DiscordDAO.getByName(DiscordUser.class, "copperlark ");
        for (String question : user4.getAnswered().keySet()) {
            System.out.println(question + ": " + user4.getAnswered().get(question).getAnswer());
        }*/
/*
      Project project = new Project();
      project.setName("Fifth");
      project.setDescription("fun 4");
      project.setStatus(Project.Status.ACTIVE);
      project.setDifficulty(Project.Difficulty.EASY);
      project.setCompleted(0.56);
      project.addCompleted(0.1);
      DiscordDAO.insert(project);


        Project project1 = DiscordDAO.getById(Project.class, 5);
        System.out.println(project1);

        Project project2 = DiscordDAO.getById(Project.class, 9);
        System.out.println(project2);
        System.out.println(DiscordDAO.getById(Project.class, 9));  //error*/

        DiscordUser user = DiscordDAO.getById(DiscordUser.class, 40);
        for (String question : user.getAnswered().keySet()) {
            System.out.println(question + ": " + user.getAnswered().get(question).getAnswer());
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

    public static <T extends Insertable> int insert(T object)  {
       String table;
       Column identificator;
       String identificatorValue;

        if (object instanceof DiscordUser) {
            DiscordUser user = (DiscordUser) object;
            table = "users";
            identificator = Column.IDSTRING;
            identificatorValue = user.getId();
        }
        else if (object instanceof Project) {
            Project project = (Project) object;
            identificator=Column.NAME;
            identificatorValue = project.getName();
            table="projects";
        }
        else  {
            System.out.println("unknown type");

            return -2;
        }

        try  {
            int id = getIdByColumn(object, identificator);
            if (id<=0)  {
                System.out.println("storing object " + identificatorValue);
                PreparedStatement createUser  = connection.prepareStatement("INSERT INTO " + table +
                        "("+ identificator.databaseLabel + ") VALUES (?);");
                createUser.setString(1, identificatorValue);

                createUser.executeUpdate();
            }

            PreparedStatement columnsStatement = connection.prepareStatement("SELECT * FROM " + table +" WHERE false;");
            ResultSet columns = columnsStatement.executeQuery();
            //columns.next();

            int result = 0;
            for (int i=1; i<=columns.getMetaData().getColumnCount(); i++)  {
                String column = columns.getMetaData().getColumnName(i);
                //System.out.println("column: " + column);
               // System.out.println(object.get(Column.getByDatabaseLabel(column).userLabel));

                if (Column.getByDatabaseLabel(column)!=null &&
                        object.get(Column.getByDatabaseLabel(column).userLabel)!=null)  {
                    PreparedStatement updateTable = connection.prepareStatement("UPDATE " + table + " SET (" + column +
                                ") = (?) WHERE " + identificator.databaseLabel + "='" + identificatorValue + "';");
                    updateTable.setObject(1, object.get(Column.getByDatabaseLabel(column).userLabel));

                    result = updateTable.executeUpdate();
                }
            }

            //Maybe not necessary
           /* System.out.println("STORING TAG: " + user.getTag());
            PreparedStatement updateOther = connection.prepareStatement("UPDATE users " +
                    "SET (" + Column.USERNAME.databaseLabel + ", " +
                    Column.TAGSTRING.databaseLabel +") = " +
                    "(?, ?) WHERE " + Column.IDSTRING.databaseLabel + "='" + user.getId() + "';");
            updateOther.setString(1, user.getName());
            updateOther.setString(2, user.getTag());

            updateOther.executeUpdate();*/

            return result;
        }
        catch (Exception e)  {
            e.printStackTrace();
            return -1;
        }
    }

    public static <T extends Insertable> T getByName(Class<T> type, String name)  {
       System.out.println("inside getByName: " + name);

       Column nameColumn;
       if (type==DiscordUser.class)  {
           nameColumn=Column.USERNAME;
       }
       else if (type==Project.class)  {
           nameColumn=Column.NAME;
       }
       else  {
           return null;
       }

       return getById(type, getIdByColumn(name, nameColumn));
    }

    public static <T extends Insertable> T getByIdString(Class<T> type, String idString)  {
       if (type==DiscordUser.class) {
           return getById(type, getIdByColumn(idString, Column.IDSTRING));
       }

       return null;
    }

    public static <T extends Insertable> T getByTagString(Class<T> type, String tagString)  {
       if (type==DiscordUser.class) {
           System.out.println("THIS TAG STRING" + tagString);

           boolean isId = false;
           if (tagString.charAt(0) == '<') {
               isId = true;
               tagString = tagString.substring(1, tagString.length() - 1);
           }

           if (tagString.charAt(0) == '@') {
               System.out.println("@@@");
               tagString = tagString.substring(1);
           }

           if (isId) {
               return getById(type, getIdByColumn(tagString, Column.IDSTRING));
           }

           return getById(type, getIdByColumn(tagString, Column.TAGSTRING));
       }
       else  {
           return null;
       }
    }

    public static <T extends Insertable> T getById(Class<T> type, int id)  {
       System.out.println("Inside getById(" + type + ", " + id + ")");

       String table;
       Insertable resultObject;

        if (type == DiscordUser.class) {
            table = "users";
            resultObject = new DiscordUser();
        }
        else if (type==Project.class) {
            table = "projects";
            resultObject = new Project();
        }
        else  {
            return null;
        }

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+ table +
                    " WHERE id=?;");
            preparedStatement.setInt(1, id);

            System.out.println(preparedStatement.toString());
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next())  {
                System.out.println("empty");
                return null;
            }

            for (int i=1; i<=result.getMetaData().getColumnCount(); i++)  {
                if (result.getString(i)!=null && !result.getString(i).equalsIgnoreCase(""))  {
                    System.out.println("i: " + i);
                   System.out.println(result.getMetaData().getColumnName(i));
                    System.out.println(result.getString(i));

                    if (Column.getByDatabaseLabel(result.getMetaData().getColumnName(i))!=null) {
                        resultObject.set(Column.getByDatabaseLabel(result.getMetaData().getColumnName(i)), result.getObject(i));
                    }
                }
            }

            //Maybe not necessary
            //resultObject.setTag(result.getString(Column.TAGSTRING.databaseLabel));

           /* user.setName(result.getString("username"));
            user.setTimezone(result.getString("timezone"));
            user.setLevel(result.getString("level"));
            user.setAge(result.getInt("age"));
            user.setCountry(result.getString("country"));*/

            return (T) resultObject;
        } catch (Exception e)  {
                e.printStackTrace();
                return null;
        }
    }

    public static <T extends Insertable> int getIdByColumn(Class<T> type, String name, Column column) {
        if (type==DiscordUser.class)  {
            DiscordUser user = new DiscordUser();
            user.set(column, name);
            return getIdByColumn(user, column);
        }

        if (type==Project.class)  {
            Project project = new Project();
            project.set(column, name);
            return getIdByColumn(project, column);
        }

        return -1;
    }

    public static <T extends Insertable> int getIdByColumn(Object object, Column column)  {
        Object value;
        value = object;

        try {
            String st = "SELECT id FROM " + column.table +
                    "WHERE " + column.databaseLabel + "='" + value + "' ORDER BY id ASC";
            System.out.println("ST: " + st);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM " + column.table +
                    " WHERE " + column.databaseLabel + "='" + value + "' ORDER BY id ASC");

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

    public static <T extends Insertable> int getIdByColumn(T object, Column column)  {
            Object value;
            value = ((Insertable) object).get(column.userLabel);

           return getIdByColumn(value, column);
    }
}

