package javastudybuddies.discordbots.singerbot;

import java.util.ArrayList;
import java.util.List;

public class Song {
        private String name;
        private Author author;
        private List<String> lyrics;

        {
            lyrics = new ArrayList<>();
        }

        //getters
        public String getName()  {return name;}
        public Author getAuthor()  {return author;}
        public List<String> getLyrics()  {return lyrics;}
        public String getLine(int n)  {return lyrics.get(n);}

        //setters
        public void setName(String name)  {this.name = name;}
        public void setAuthor(Author author)  {this.author = author;}
        public void setLyrics(List<String> lyrics)  {this.lyrics = lyrics;}
        public void addLine(String line)  {lyrics.add(line);}
}
