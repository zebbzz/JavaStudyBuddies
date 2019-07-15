package javastudybuddies.discordbots.projectsbot;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

//edit commands:
//create project "name", "description"
//join project 'name"

//view commands:
//view projects
//view projects by @usertag
//view project "name"


public class ProjectsBot extends ListenerAdapter  {
    public void onMessageReceived(MessageReceivedEvent event) {
        executeCommand(event.getMessage().getContentRaw(), event);

    }

    public void executeCommand(String command, MessageReceivedEvent event)  {
        if (command.charAt(0)!='>') {
            return;
        }

        String[] args = command.split(" ");
        Guild guild = event.getGuild();
        if (command.startsWith(">create project"))  {

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
