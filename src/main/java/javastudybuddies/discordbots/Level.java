package javastudybuddies.discordbots;

import java.util.ArrayList;
import java.util.List;

public class Level {
        String name;
        String description;

        public Level(String name, String description)  {
            this.name = name;
            this.description = description;
        }

        public void setName(String name)  {this.name = name;}
        public void setDescription(String description)  {this.description = description;}

        public String getName()  {return name;}
        public String getDescription()  {return description;}

    public boolean inList(List<? extends Level> list)  {
           return inList(list, name);
    }

    public static boolean inList(List<? extends Level> list, String name)  {
            for (Level level: list)  {
                if (level.getName().equalsIgnoreCase(name))  {
                    return true;
                }
            }

        return false;
    }

    public static void main(String[] args) {
        Level level1 = new Level("Advanced", "gdf");
        List<Level> list = new ArrayList<>();
        list.add(level1);
        list.add(new Level("advanced", "fs"));

        System.out.println(new Level("Advanced", "").inList(list));
    }
}
