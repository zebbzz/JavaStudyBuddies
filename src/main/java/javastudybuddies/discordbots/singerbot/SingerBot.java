package javastudybuddies.discordbots.singerbot;

import javastudybuddies.discordbots.welcomebot.WelcomeBot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SingerBot extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src", "main", "resources", "tokens", "SingerBot.token");
        System.out.println("path: " + path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
        String token = br.readLine();
        br.close();

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new SingerBot());
    }

    public enum Mode  {LEARN, SING;}
    private Mode mode;
    private Song currentSong;
    private int bufferCount;

    public void learn(String input)  {
        String[] lines = input.split("\n");
        for (String line: lines) {
            currentSong.addLine(line);
        }
        /*bufferCount++;

        System.out.println(input + ": " + bufferCount);
        if (bufferCount>2)  {
            SingerDAO.updateLines(currentSong); //add insertion if not present?
            System.out.println("updating");
        }*/
    }

    public String sing(String previousLine)  {


        return "";
    }

    public String sing()  {
        return sing(currentSong.getLyrics().size());
    }

    public String sing(int n)  {
        SingerDAO.updateLines(currentSong);

        StringBuilder message = new StringBuilder();
        for (int i=0; i<Math.min(n, currentSong.getLyrics().size()); i++)  {
            message.append(currentSong.getLyrics().get(i) + "\n");
        }

        return message.toString();
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String input = event.getMessage().getContentRaw();
        String[] args = input.split(" ");

        if (!args[0].equalsIgnoreCase("!s"))  {
            return;
        }


        if (args[1].equalsIgnoreCase("learn"))  {
            mode = Mode.LEARN;
            currentSong = new Song();
            event.getChannel().sendMessage("Ready to learn!").queue();
            return;
        }


         if (args[1].equalsIgnoreCase("sing"))  {
            mode = Mode.SING;
            currentSong = new Song();
            event.getChannel().sendMessage("Ready to sing a song!").queue();
            return;
        }

        if (args[1].equalsIgnoreCase("song"))  {
            int firstQuote;
            int lastQuote;
            String songName = "";
            String songAuthor = "";
            try  {
                firstQuote = input.indexOf("\"");
                lastQuote = input.lastIndexOf("\"");
                songName = input.substring(firstQuote+1, lastQuote);
                songAuthor = input.substring(input.indexOf("-")+1);
            }
            catch (Exception e)  {
                e.printStackTrace();
                event.getChannel().sendMessage("You should specify song name and author").queue();
            }

            if (songAuthor.charAt(0)==' ')  {
                songAuthor = songAuthor.substring(1);
            }

            currentSong = new Song();
            currentSong.setName(songName);
            currentSong.setAuthor(new Author(songAuthor));

            String message = (mode == Mode.SING ? "Singing" : "Recording");
            message +="\"" + currentSong.getName() + "\" by " + currentSong.getAuthor().getName();
            event.getChannel().sendMessage(message).queue();
            return;
        }

        if (args[1].equalsIgnoreCase("done"))  {
            SingerDAO.updateLines(currentSong);
            event.getChannel().sendMessage("Stored \"" + currentSong.getName() + "\" by " + currentSong.getAuthor().getName() +
                " in a database").queue();
            return;
        }

        if (mode==Mode.LEARN)  {
            learn(input.substring(3));
        }
        else if (mode==Mode.SING)  {
            String message = sing(3);
            System.out.println(message);


            event.getChannel().sendMessage(message).queue();
        }
    }
}
