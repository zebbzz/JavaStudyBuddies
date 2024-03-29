package javastudybuddies.discordbots.projectsbot.entities;

import javastudybuddies.discordbots.entities.Column;
import javastudybuddies.discordbots.entities.DiscordUser;
import javastudybuddies.discordbots.entities.Insertable;
import javastudybuddies.discordbots.projectsbot.ProjectsBot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Project implements Insertable {
        private String name;
        private String description;
        private String url;
        private List<DiscordUser> users;

        private Difficulty difficulty;
        private Status status;
        private double completed;
        private Type type;

        public enum Difficulty {
<<<<<<< HEAD
            EASY, MEDIUM, HARD;

        public static Difficulty getByName(String name)  {
                for (Difficulty e : values()) {
                        if (e.name().equalsIgnoreCase(name)) {
                                return e;
                        }
                }

                return null;
        }
=======
                EASY, MEDIUM, HARD;

                public static Difficulty getByName(String name)  {
                        for (Difficulty e : values()) {
                                if (e.name().equalsIgnoreCase(name)) {
                                        return e;
                                }
                        }

                        return null;
                }
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
        }

        public enum Status {
                ACTIVE, ON_HOLD, COMPLETED, ABANDONED;
        }

        public enum Type  {
                GROUP, INDIVIDUAL;
        }

        {
                users = new ArrayList<>();
        }

        //setters
        public void setName(String name)  {this.name = name;}
        public void setDescription(String description)  {this.description = description;}
        public void setDifficulty(Difficulty difficulty)  {this.difficulty = difficulty;}
        public void setUrl(String url)  {this.url = url;}
        public void setStatus(Status status)  {this.status = status;}
        public void setCompleted(double completed)  {this.completed = completed;}
        public void setType(Type type)  {this.type = type;}

        //adders
        public void addUser(DiscordUser user)  {
                users.add(user);
        }
        public void addCompleted(double value)  {completed+=value;}

        //getters
        public String getName()  {return name;}
        public String getDescription()  {return description;}
        public List<DiscordUser> getUsers()  {return users;}
        public Difficulty getDifficulty()  {return difficulty;}
        public String getUrl()  {return url;}
        public Status getStatus()  {return status;}
        public double getCompleted()  {return completed;}
        public Type getType()  {return type;}

        public Object get(String key)  {
                switch (key)  {
                        case "name":
                                return getName();
                        case "description":
                                return getDescription();
                        case "users":
                                return getUsers();
                        case "difficulty":
                                return difficulty==null ? null : difficulty.name();
                        case "url":
                                return getUrl();
                        case "status":
                                return status==null ? null : status.name();
                        case "completed":
                                return completed;
                        case "type":
                                return type==null ? null : type.name();
                }

                return null;
        }

        public String getTable()  {return "projects";}

        public String getIdentificator()  {return getName();}

        public void set(Column column, Object value)  {
                String key = column.userLabel;
                switch (key)  {
                        case "name":
                                setName((String) value);
                                break;
                        case "description":
                                setDescription((String) value);
                                break;
                        case "users":
                                break;
                        case "difficulty":
                                setDifficulty(Difficulty.valueOf((String) value));
                                break;
                        case "url":
                                setUrl((String) url);
                                break;
                        case "type":
                                setType(Type.valueOf((String) value));
                                break;
                        case "completed":
                                setCompleted(((BigDecimal) value).doubleValue());
                                break;
                        case "status":
                                setStatus(Status.valueOf((String) value));
                }
        }

        public String toString()  {
                StringBuilder result = new StringBuilder();
                result.append("name: " + name);
                result.append("\ndescription: " + description);
                result.append("\ndiff: " + difficulty);
                result.append("\nstatus: " + status);
                result.append("\ntype: " + type);
                result.append("\ncompleted: " +completed);

                return result.toString();
        }

        public static void main(String[] args) {
                System.out.println(Status.ACTIVE.name());
        }
}