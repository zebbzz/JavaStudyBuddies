package javastudybuddies.discordbots.projectsbot;

import javastudybuddies.discordbots.DiscordDAO;
import javastudybuddies.discordbots.projectsbot.entities.Project;
import javastudybuddies.discordbots.welcomebot.WelcomeBot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//edit commands:
//create project "name", "description"
//join project 'name"

//view commands:
//view projects
//view projects by @usertag
//view project "name"


public class ProjectsBot extends ListenerAdapter  {
    //for testing
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(ProjectsBot.class.getResourceAsStream("/tokens/ProjectsBot.token")));
        String token = br.readLine();
        br.close();

        System.out.println("token: " + token);

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new ProjectsBot());
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        executeCommand(event.getMessage().getContentRaw(), event);
    }

    public void executeCommand(String command, MessageReceivedEvent event)  {
        if (command.charAt(0)!='>') {
            return;
        }

        String[] args = command.split(" ");
        Guild guild = event.getGuild();

        if (command.equalsIgnoreCase(">test"))  {
            event.getChannel().sendMessage("test success").queue();
            return;
        }

        if (command.startsWith(">create project"))  {
            Project project = new Project();
            String name = args[2].substring(1, args[2].length()-1);
            String description = args[3].substring(1, args[3].length()-1);

            project.setName(name);
            project.setDescription(description);
            project.setStatus(Project.Status.ACTIVE);
            project.setCompleted(0);

            if (args[args.length-1].equalsIgnoreCase("group"))  {
                project.setType(Project.Type.GROUP);
            }
            else  {
                project.setType(Project.Type.INDIVIDUAL);
            }

            DiscordDAO.insert(project);
        }
        else if (command.startsWith(">join project"))  {

        }
        else if (command.startsWith(">view projects"))  {

        }
        else if (command.startsWith(">view projects by"))  {

        }
        else if (command.startsWith(">view project"))  {

        }

    }


}
