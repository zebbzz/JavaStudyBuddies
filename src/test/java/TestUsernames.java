import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;

public class TestUsernames {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\JavaStudyBuddies\\src\\main\\java\\javastudybuddies\\discordbots\\tokens\\WelcomeBotToken.txt";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String line = br.readLine();
        System.out.println(line);
    }


}
