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

        private DIFFICULTY difficulty;
        private STATUS status;

        public enum DIFFICULTY  {
            EASY, MEDIUM, HARD;
        }

        public enum STATUS  {
                ACTIVE, FROZEN, COMPLETED, ABANDONED;
        }

        {
            users = new ArrayList<>();
        }

        //setters
        public void setName(String name)  {this.name = name;}
        public void setDescription(String description)  {this.description = description;}
        public void setDifficulty(DIFFICULTY difficulty)  {this.difficulty = difficulty;}
        public void setUrl(String url)  {this.url = url;}
        public void setStatus(STATUS status)  {this.status = status;}

        //adders
        public void addUser(DiscordUser user)  {
            users.add(user);
        }

        //getters
        public String getName()  {return name;}
        public String getDescription()  {return description;}
        public List<DiscordUser> getUsers()  {return users;}
        public DIFFICULTY getDifficulty()  {return difficulty;}
        public String getUrl()  {return url;}
        public STATUS getStatus()  {return status;}

        public Object get(String key)  {
                switch (key)  {
                        case "name":
                                return getName();
                        case "desciption":
                                return getDescription();
                        case "users":
                                return getUsers();
                        case "difficulty":
                                return getDifficulty();
                        case "url":
                                return getUrl();
                        case "status":
                                return status;
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
}

