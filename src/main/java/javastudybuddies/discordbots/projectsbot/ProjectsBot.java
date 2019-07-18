package javastudybuddies.discordbots.projectsbot;

import javastudybuddies.discordbots.DiscordDAO;
import javastudybuddies.discordbots.entities.Column;
import javastudybuddies.discordbots.projectsbot.entities.Project;
import javastudybuddies.discordbots.welcomebot.WelcomeBot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//edit commands:
//create project "name", "description", "difficulty"
//join project 'name"

//view commands:
//view projects
//view projects by @usertag
//view project "name"


<<<<<<< HEAD
public class ProjectsBot extends ListenerAdapter  {
    private enum Command  {
=======
public class ProjectsBot extends ListenerAdapter {
    private enum Command {
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
        CREATE_PROJECT("create project \"name\", \"description\", \"difficulty\""),
        JOIN_PROJECT("join project 'name\"");

        public final String syntax;

<<<<<<< HEAD
        Command(String syntax)  {
=======
        Command(String syntax) {
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
            this.syntax = syntax;
        }
    }

<<<<<<< HEAD
    private enum Error  {
=======
    private enum Error {
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
        SYNTAX_ERROR("Wrong syntax", "Please, use this syntax: \n", Command.CREATE_PROJECT.syntax,
                "https://otvet.imgsmail.ru/download/73344370_48af568c06460e6fdf4ca4c6eb7349ba_800.jpg"),
        ALREADY_EXISTS_ERROR("Such project already exists", "Pick another name", "",
                "https://i.pinimg.com/originals/0a/51/7e/0a517e8d990172eb1302769adcd49656.jpg"),
        WRONG_DIFFICULTY("Wrong difficulty", "Please, pick one of these: \n", "Easy, Medium or Hard",
                "https://imgflip.com/s/meme/Star-Wars-Yoda.jpg");

        public final String description;
        public final String fieldName;
        public final String fieldContent;
        public final String picture;
        public final EmbedBuilder eb;

<<<<<<< HEAD
        Error(String description, String fieldName, String fieldContent, String picture)  {
            this.description = description;
            this.fieldName = fieldName;
            this.fieldContent=fieldContent;
=======
        Error(String description, String fieldName, String fieldContent, String picture) {
            this.description = description;
            this.fieldName = fieldName;
            this.fieldContent = fieldContent;
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
            this.picture = picture;

            eb = new EmbedBuilder();
            eb.setTitle(errorTitle);
            eb.setDescription(description);
            eb.addField(fieldName, fieldContent, false);
            eb.setThumbnail(picture);
            eb.setColor(Color.red);
        }
    }

    private static String errorTitle = "Error";


    //for testing
    public static void main(String[] args) throws Exception {
<<<<<<< HEAD
        BufferedReader br = new BufferedReader(new InputStreamReader(ProjectsBot.class.getResourceAsStream("/tokens/ProjectsBot.token")));
        String token = br.readLine();
        br.close();

        System.out.println("tokens: " + token);
=======
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NjAwNDg0MjYxMzQyMjE2MTk3.XS35qQ.nJ-j8meL8YSwll1sqDfzAz-nxtM");
        JDA jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new ProjectsBot());
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        executeCommand(event.getMessage().getContentRaw(), event);
    }

<<<<<<< HEAD
    public void executeCommand(String command, MessageReceivedEvent event)  {
        if (event.getAuthor().isBot())  {
            return;
        }

        if (command.charAt(0)!='>') {
            return;
        }

        if (command.equalsIgnoreCase(">test"))  {
            event.getChannel().sendMessage("test success").queue();
            return;
        }

        if (command.startsWith(">create project"))  {
            Project project = new Project();
            String[] args = command.split(" ");

            if (args.length<5)  {
                System.out.println("arguments too few");
                event.getChannel().sendMessage(Error.SYNTAX_ERROR.eb.build()).queue();
                return;
            }

            if (args[2].charAt(0)!='\"' || args[2].charAt(args[2].length()-2)!='\"' ||
                    args[3].charAt(0)!='\"' || args[3].charAt(args[3].length()-2)!='\"' ||
                    args[4].charAt(0)!='\"' || args[4].charAt(args[4].length()-1)!='\"')  {
                System.out.println("commas problem");
                event.getChannel().sendMessage(Error.SYNTAX_ERROR.eb.build()).queue();
                return;
            }


            String name = args[2].substring(1, args[2].length()-2);

            if (DiscordDAO.getIdByColumn(Project.class, name, Column.NAME)>0)  {
                event.getChannel().sendMessage(Error.ALREADY_EXISTS_ERROR.eb.build()).queue();
                return;
            }

            String difficulty = args[4].substring(1, args[4].length()-1);
            if (Project.Difficulty.getByName(difficulty)==null)  {
                    event.getChannel().sendMessage(Error.WRONG_DIFFICULTY.eb.build()).queue();
                    return;
            }

            String description = args[3].substring(1, args[3].length()-2);

            project.setName(name);
            project.setDescription(description);
            project.setStatus(Project.Status.ACTIVE);
            project.setCompleted(0);
            project.setDifficulty(Project.Difficulty.valueOf(difficulty.toUpperCase()));

            if (args[args.length-1].equalsIgnoreCase("group"))  {
                project.setType(Project.Type.GROUP);
            }
            else  {
                project.setType(Project.Type.INDIVIDUAL);
            }

            DiscordDAO.insert(project);

            event.getChannel().sendMessage("Successful insert!").queue();
        }
        else if (command.startsWith(">join project"))  {

        }
        else if (command.startsWith(">view projects"))  {

=======
    public void executeCommand(String command, MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (command.charAt(0) != '>') {
            return;
        }

        if (command.equalsIgnoreCase(">test")) {
            event.getChannel().sendMessage("test success").queue();
            return;
>>>>>>> b858d8ab9c06ee2645a0dda716d9b5e14a6db11d
        }

        if (command.startsWith(">create project")) {
            Project project = new Project();
            String newcommand = command.substring(16);
            String[] args = newcommand.split(", ");
            int commas = 0;
            for (int i = 0; i < command.length(); i++) {
                if (command.charAt(i) == ',') {
                    commas++;
                }
            }
            System.out.println(commas);
            if (args.length < 3) {
                System.out.println("arguments too few");
                event.getChannel().sendMessage(Error.SYNTAX_ERROR.eb.build()).queue();
                return;
            }

            if (!(commas == 2 || commas == 3)) {
                System.out.println("commas problem");
                event.getChannel().sendMessage(Error.SYNTAX_ERROR.eb.build()).queue();
                return;
            }

            String name = args[0];

            if (DiscordDAO.getIdByColumn(Project.class, name, Column.NAME) > 0) {
                event.getChannel().sendMessage(Error.ALREADY_EXISTS_ERROR.eb.build()).queue();
                return;
            }

            String difficulty = args[2];
            if (Project.Difficulty.getByName(difficulty) == null) {
                event.getChannel().sendMessage(Error.WRONG_DIFFICULTY.eb.build()).queue();
                return;
            }

            String description = args[1];

            project.setName(name);
            project.setDescription(description);
            project.setStatus(Project.Status.ACTIVE);
            project.setCompleted(0);
            project.setDifficulty(Project.Difficulty.valueOf(difficulty.toUpperCase()));
            if (args[3].equalsIgnoreCase("group")) {
                project.setType(Project.Type.GROUP);
            } else {
                project.setType(Project.Type.INDIVIDUAL);
            }
            DiscordDAO.insert(project);

            event.getChannel().sendMessage("Successful insert!").queue();
        } else if (command.startsWith(">join project")) {

        } else if (command.startsWith(">view projects")) {

        } else if (command.startsWith(">view projects by")) {

        } else if (command.startsWith(">view project")) {

        }

    }


}
