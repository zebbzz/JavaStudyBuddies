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
        Path path = Paths.get("src", "main", "resources", "tokens", "WelcomeBot.token");
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

    public void learn(String line)  {

    }

    public String sing(String previousLine)  {
        return "";
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (!args[0].equalsIgnoreCase("!singer"))  {
            return;
        }

        if (args[1].equalsIgnoreCase("mode"))  {
            if (args[2].equalsIgnoreCase("learn"))  {
                mode = Mode.LEARN;
            }
            else if (args[2].equalsIgnoreCase("sing"))  {
                mode = Mode.SING;
            }
        }


    }
}
