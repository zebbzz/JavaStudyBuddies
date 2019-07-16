package javastudybuddies.discordbots.projectsbot.entities;

import javastudybuddies.discordbots.entities.Column;
import javastudybuddies.discordbots.entities.DiscordUser;
import javastudybuddies.discordbots.entities.Insertable;

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

        public enum Difficulty {
            EASY, MEDIUM, HARD;
        }

        public enum Status {
                ACTIVE, ON_HOLD, COMPLETED, ABANDONED;
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

        public Object get(String key)  {
                switch (key)  {
                        case "name":
                                return getName();
                        case "desciption":
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
                        case "desciption":
                                setDescription((String) value);
                                break;
                        case "users":
                                break;
                        case "difficulty":
                                break;
                        case "url":
                                setUrl((String) url);
                                break;
                }
        }

        public static void main(String[] args) {
                System.out.println(Status.ACTIVE.name());
        }
}

