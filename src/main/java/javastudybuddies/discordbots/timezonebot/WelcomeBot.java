package javastudybuddies.discordbots.timezonebot;


import javastudybuddies.discordbots.DiscordDAO;
import javastudybuddies.discordbots.DiscordUser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class WelcomeBot extends ListenerAdapter {
        String helpMessage;
        Map<String, DiscordUser> currentUsers;

        private enum Level  {
                BEGINNER,
                ELEMENTARY,
                INTERMEDIATE,
                UPPER_INTERMEDIATE,
                ADVANCED,
                PROFICIENT
        }

        {
                helpMessage = "Every command must start with !welcome";
                currentUsers = new LinkedHashMap<>();
        }

        private static Map<String, String>  levels = new LinkedHashMap<>();

        static  {
                levels.put("Beginner", "has only just started");
                levels.put("Elementary", "knows some syntax, loops, conditions");
                levels.put("Pre-Intermediate", "knows what an ArrayList is, writes multi-class programs");
                levels.put("Intermediate", "knows the basics and some more advanced stuff, oop, algorithms");
                levels.put("Upper-intermediate", "knows some frameworks/libraries, can write useful Android/web stuff");
                levels.put("Advanced", "has recently gotten a Java job");
                levels.put("Proficient", "has been employed as Java dev for several years");
        }

        public static void main(String[] args) throws LoginException {
                JDABuilder builder = new JDABuilder(AccountType.BOT);
                builder.setToken("NTg0MDk0NjA3Njc2MTQ1NjY2.XPF73g.9T0h_oDMAykgFaHVytrdtPpV4mc");

                builder.addEventListener(new WelcomeBot());
                builder.buildAsync();
        }

        public void onMessageReceived(MessageReceivedEvent event)  {
                String[] args = event.getMessage().getContentRaw().split(" ");

                if (event.getMessage().getAuthor().getName().equalsIgnoreCase("TimezoneBot"))  {
                        return;
                }

                 if (!args[0].equalsIgnoreCase("!welcome"))  {
                        event.getChannel().sendMessage(helpMessage).queue();
                }

                else if (args[1].equalsIgnoreCase("start"))  {
                         DiscordUser user = new DiscordUser();
                         user.setName(event.getMessage().getAuthor().getName());

                         if (!currentUsers.containsKey(user.getName()))  {
                                currentUsers.put(user.getName(), user);
                         }

                        event.getChannel().sendMessage("Hello, @" + user.getName() + "!").queue();
                        event.getChannel().sendMessage("Welcome to JavaStudyBuddies!").queue();
                        event.getChannel().sendMessage("Please, tell us a little about yourself:").queue();
                        event.getChannel().sendMessage("1. What is your Java level?").queue();
                        event.getChannel().sendMessage("please, pick one of these options and reply in this format: ```!welcome level [beginner]```").queue();

                        for (String level: levels.keySet())  {
                                event.getChannel().sendMessage("**" + level + "**" + ": *" + levels.get(level) + "*").queue();
                        }
                }

                else if (!currentUsers.containsKey(event.getMessage().getAuthor().getName()))  {
                        event.getChannel().sendMessage("Sorry, @" + event.getMessage().getAuthor().getName() + ", you haven't started yet").queue();
                }

               else if (args[1].equalsIgnoreCase("level")) {
                        try {
                                if (levels.containsKey(args[2]))  {
                                        currentUsers.get(event.getMessage().getAuthor().getName()).setLevel(args[2]);

                                        event.getChannel().sendMessage("2. What is your timezone?").queue();
                                        event.getChannel().sendMessage("please, reply in this format: ```!welcome timezone URC+[1]```").queue();

                                        return;
                                }
                                else  {
                                        event.getChannel().sendMessage("Sorry, @" + event.getMessage().getAuthor().getName() + ", you should choose one of the above levels").queue();
                                }

                        }
                        catch (Exception e) {
                                event.getChannel().sendMessage(helpMessage).queue();
                        }
                }

               else if (args[1].equalsIgnoreCase("timezone"))  {
                         if (args[2].startsWith("UTC+"))  {
                                 try  {
                                         int hours = Integer.parseInt(args[2].substring(4));
                                         currentUsers.get(event.getMessage().getAuthor().getName()).setTimezone(args[2]);

                                         DiscordDAO.insert(currentUsers.get(event.getMessage().getAuthor().getName()));
                                         currentUsers.remove(event.getMessage().getAuthor().getName());
                                 }
                                 catch (Exception e)  {
                                         event.getChannel().sendMessage("Not a valid reply").queue();
                                 }
                         }
                }

        }
}

