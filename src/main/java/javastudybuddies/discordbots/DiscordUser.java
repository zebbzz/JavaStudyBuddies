package javastudybuddies.discordbots;

import java.util.ArrayList;
import java.util.List;

public class DiscordUser {
        private String username;
        private String level;
        private String country;
        private String timezone;

        private ArrayList<LeetcodeTask> solved;

        //getters
        public String getName() {return username;}
        public List<LeetcodeTask> getLeetcodeTasks()  {return solved;}
        public String getLevel()  {return level;}
        public String getCountry()  {return country;}
        public String getTimezone()  {return timezone;}

        //setters
        public void setName(String username)  {this.username = username;}
        public void setLevel(String level)  {this.level = level;}
        public void setCountry(String country)  {this.country = country;}
        public void setTimezone(String timezone)  {this.timezone = timezone;}
}
