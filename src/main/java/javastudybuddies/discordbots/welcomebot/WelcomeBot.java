package javastudybuddies.discordbots.welcomebot;


import javastudybuddies.discordbots.*;
import javastudybuddies.discordbots.entities.DiscordMessage;
import javastudybuddies.discordbots.entities.DiscordUser;
import javastudybuddies.discordbots.welcomebot.entities.Action;
import javastudybuddies.discordbots.entities.Column;
import javastudybuddies.discordbots.welcomebot.entities.Level;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import other.Weapon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

//TODO: several-word countries
//TODO: age limits
//TODO: last argument update: breaks the links
//TODO: skip option
//TODO: nicer formattingml

public class WelcomeBot extends ListenerAdapter {
    String helpMessage = "Every command must start with !w";

    Guild javaServer;
    Map<String, TextChannel> channels;

    Map<String, DiscordUser> currentUsers;

    Map<String, Action> actions;
    Action currentAction;

    private static List<Level> levels = new ArrayList<>();
    private static List<String> tech = new ArrayList<>();


    static {
        levels.add(new Level("Beginner", "has only just started"));
        levels.add(new Level("Elementary", "knows some syntax"));
        levels.add(new Level("Pre-Intermediate", "knows what an ArrayList is, writes multi-class programs"));
        levels.add(new Level("Intermediate", "knows the basics and some more advanced stuff, oop, algorithms"));
        levels.add(new Level("Upper-Intermediate", "knows some frameworks/libraries, can write useful Android/web stuff"));
        levels.add(new Level("Advanced", "has recently gotten a Java job"));
        levels.add(new Level("Proficient", "has been employed as Java dev for several years"));

        tech.add("Android");
        tech.add("Spring");
        tech.add("Other");
        tech.add("Unsure");
    }

    {
        channels = new HashMap<>();
        currentUsers = new LinkedHashMap<>();


        actions = new HashMap<>();
        //start action
        actions.put("start", new Action() {
            @Override
            public void setup()  {
                List<DiscordMessage> startMessages = new ArrayList<>();

                EmbedBuilder startEmbed = new EmbedBuilder();
                startEmbed.setTitle("Hello!");
                startEmbed.addField("Welcome to JavaStudyBuddies!\n",
                        "We aspire to always improve this server, which is why we'd like to learn about our audience more.\n" +
                                "Please, tell us a little about yourself", true);
                //startEmbed.setColor(Color.RED);

                startMessages.add(new DiscordMessage(startEmbed));

                this.messages = startMessages;
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                return true;
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                System.out.println("inside start" + this.messages + this.messages);
                super.process(event, input, user, type, privacy);
                if (!currentUsers.containsKey(user.getId())) {
                    currentUsers.put(user.getId(), user);
                }
            }

            @Override
            public void perform(String input, DiscordUser user, MessageReceivedEvent event, Narration narration, Action.Privacy privacy)  {
                //talk(event, privacy);
                super.perform(input, user, event, narration, privacy);
            }
        });

        //level action
        actions.put("level", new Action() {
            @Override
            public void setup()  {
                this.errorMessage = "You should choose one of the above levels";

                String levelMessage = "";
                int count = 0;
                for (int i=0; i<levels.size(); i++) {
                    levelMessage += ("**" + (count++) + ". " + levels.get(i).getName() + "**" + ": *" + levels.get(i).getDescription() + "*\n");

                }

                List<DiscordMessage> levelMessages = new ArrayList<>();

                EmbedBuilder levelEmbed1 = new EmbedBuilder();
                levelEmbed1.setTitle("**1. What is your Java level?**\n");
                levelEmbed1.setDescription("*please, pick one of these options, example: *```!w level Beginner``` or ```!w level 0```\n");
                levelEmbed1.addField("Levels: ", levelMessage, true);
                // levelEmbed1.setThumbnail("http://xoroshiy.ru/uploads/posts/2018-11/rik-i-morti-okazalsya-samym-zhestokim-serialom-po-kolichestvu-smertey-na-seriyu_1.jpg");
                //levelEmbed1.setColor(Color.YELLOW);

                EmbedBuilder levelEmbed2 = new EmbedBuilder();
                String levelImage = "http://xoroshiy.ru/uploads/posts/2018-11/rik-i-morti-okazalsya-samym-zhestokim-serialom-po-kolichestvu-smertey-na-seriyu_1.jpg";
                levelEmbed2.setThumbnail(levelImage);
                //levelEmbed2.setColor(new Color(54, 57, 63));

                EmbedBuilder emptyLine = new EmbedBuilder();
                emptyLine.addBlankField(false);

                levelMessages.add(new DiscordMessage(levelEmbed1));
                //levelMessages.add(new DiscordMessage(emptyLine));
                levelMessages.add(new DiscordMessage(levelEmbed2));

                this.messages = levelMessages;
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                String[] args = input.split(" ");

                try {
                    int level = Integer.parseInt(args[2]);

                    return level < levels.size();
                } catch (Exception e) {
                    return Level.inList(levels, args[2]);
                }
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                super.process(event, input, user, type, privacy);
                String[] args = input.split(" ");

                try {
                    user.setLevel(levels.get(Integer.parseInt(args[2])).getName());
                } catch (Exception e) {
                    user.setLevel(args[2].substring(0, 1).toUpperCase() + args[2].substring(1).toLowerCase());
                }
            }
        });

        //java goals
        actions.put("goal", new Action()  {
            @Override
            public void setup()  {
                this.errorMessage = "Not a valid reply";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("**2. What is your main Java goal?**\n");
                embed.addField("*example: *", "```!w goal Fun``` or ```!w goal Employment``` or whatever", false);
                embed.setThumbnail("https://vignette.wikia.nocookie.net/rickandmorty/images/6/67/Butter_Robot_Picture.png");

                addMessage(new DiscordMessage(embed));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                String[] args = input.split(" ");

                return args.length >=3;
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                String[] args = input.split(" ");

                user.setGoal(args[2]);
            }
        });

        //technology
        actions.put("tech", new Action()  {
            @Override
            public void setup()  {
                this.errorMessage = "Not a valid reply";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("**2. What is your preferred technology?**\n");
                embed.setDescription("*example: * ```!w tech Android``` or ```!w tech 0```");

                String techMessage = "";
                int count = 0;
                for (int i=0; i<tech.size(); i++) {
                    techMessage += ("**" + (count++) + ". " + tech.get(i) + "**\n");
                }

                embed.addField("Techs:", techMessage, false);
                embed.setThumbnail("https://coubsecure-s.akamaihd.net/get/b197/p/coub/simple/cw_timeline_pic/d6628d83b99/2dc69a02d432819195f6d/big_1522778593_image.jpg");

                addMessage(new DiscordMessage(embed));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                String[] args = input.split(" ");

                try  {
                    int tech = Integer.parseInt(args[2]);

                    return true;
                }
                catch (Exception e)  {
                        return tech.contains(args[2]);
                }
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                String[] args = input.split(" ");

                try  {
                    int techNumber = Integer.parseInt(args[2]);

                    user.setTech(tech.get(techNumber));
                }
                catch (Exception e)  {
                    user.setTech(args[2]);
                }
            }
        });

        //timezone action
        actions.put("timezone", new Action() {
            @Override
            public void setup()  {
                this.errorMessage ="Not a valid reply";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("**2. What is your timezone?**\n");
                embed.setDescription("You could use something like this website to find out: https://time.is. Or, you could always google ```UTC time now``` and see how many hours ahead or behind you are.");
                embed.addField("*example*: ", "```!w timezone UTC+1```\n", true);
                embed.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/8/88/World_Time_Zones_Map.png");

                addMessage(new DiscordMessage(embed));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                String[] args = input.split(" ");

                try {
                    int hours = Integer.parseInt(args[2].substring(4));

                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                super.process(event, input, user, type, privacy);
                String[] args = input.split(" ");

                user.setTimezone(args[2]);
            }
        });

        //age action
        actions.put("age", new Action() {
            @Override
            public void setup()  {
                this.errorMessage = "Not a valid reply";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Additionary questions");
                embed.setDescription("*The following questions are not obligatory. However, if you won't answer those, you won't be able to see the answers of other people*\n");

                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.addField("**3. What is you age?**\n", "example: ```!w age 22``` or ```!w age skip```\n", false);
                embed2.setThumbnail("https://y.yarn.co/2c026467-dedb-4cb8-8713-d5159fe3c404_screenshot.jpg");

                addMessage(new DiscordMessage(embed));
                addMessage(new DiscordMessage(embed2));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                String[] args = input.split(" ");

                try {
                    int age = Integer.parseInt(args[2]);

                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                super.process(event, input, user, type, privacy);
                String[] args = input.split(" ");

                user.setAge(Integer.parseInt(args[2]));
            }
        });

        //country action
        actions.put("country", new Action() {
            @Override
            public void setup()  {
                this.errorMessage = "Not a valid reply";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Where are you from?\n");
                embed.setDescription("example: ```!w country USA```");
                embed.setThumbnail("https://filmdaily.co/wp-content/uploads/2018/06/rick-and-morty-travel-guide.jpg");

                addMessage(new DiscordMessage(embed));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                return true;
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                super.process(event, input, user, type, privacy);
                String[] args = input.split(" ");

                user.setCountry(args[2]);

                if (type==Narration.NEXT) {
                    talk(event, "Successful complete! About to update your info. Does it look OK to you?\n", privacy);
                    talk(event, display(user), privacy);
                }
            }
        });

        //final action
        actions.put("update", new Action() {
            @Override
            public void setup()  {
                this.errorMessage = "Something went wrong witht the update";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Updating");
                embed.addField("If so, please, type", " ```!w update```", false);
                //embed.setThumbnail("https://cdn.app.compendium.com/uploads/user/e7c690e8-6ff9-102a-ac6d-e4aebca50425/f0499405-1197-4b43-b7c5-40548eeb9f34/File/f693a60f01914b4142aec788702e1bd9/autonomous_database.jpg");

                addMessage(new DiscordMessage(embed));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                if (user.allAnswered()) {
                    return true;
                }

                if (DiscordDAO.getIdByColumn(user.getId(), Column.IDSTRING)>0)  {
                    return true;
                }

                talk(event, "```diff\nYou didn't answer these questions:```"  + user.questionsToAnswer(), privacy);
                return false;
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                super.process(event, input, user, type, privacy);
                System.out.println("inserting: " + user.getName());
                DiscordDAO.insert(user);
                currentUsers.remove(user.getId());
            }
        });

        //error action
        actions.put("error", new Action() {
            @Override
            public void setup()  {
                this.errorMessage = "something went wrong with the error";
                addMessage(new DiscordMessage("Wrong command. There are only 4 commands: start, level, timezone and age"));
            }

            @Override
            public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy) {
                return true;
            }

            @Override
            public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {

            }
        });

        //view data
        actions.put("view", new Action()  {
            @Override
            public void setup()  {
                this.errorMessage = "Please, check the second parameter";
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("How to view anyone's answers");
                embed.setDescription("To view a user's answers, use this command: ```!w view me``` or ```!w view @username```");
                embed.setThumbnail("https://pp.userapi.com/c624918/v624918097/15f13/B2tpPRsHzMQ.jpg");

                addMessage(new DiscordMessage(embed));
            }

            @Override
                public boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy)  {
                    System.out.println("in view check");

                    String[] args = input.split(" ");
                    if (args[2].equalsIgnoreCase("me"))  {
                        return true;
                    }
                    else  {
                        System.out.println("WRONG THIS TAG STRING: " + args[2]);

                        return DiscordDAO.getByTagString(DiscordUser.class, args[2])!=null ||
                                    DiscordDAO.getByName(DiscordUser.class, args[2])!=null;
                    }

                }

                @Override
                public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy) {
                    System.out.println("in view process");

                    String[] args = input.split(" ");

                    DiscordUser resultUser;
                    if (args[2].equalsIgnoreCase("me"))  {
                         resultUser = DiscordDAO.getByIdString(DiscordUser.class, user.getId());
                    }
                    else if (args[2].contains("@")) {
                         resultUser = DiscordDAO.getByTagString(DiscordUser.class, args[2]);
                    }
                    else  {
                        System.out.println("GET BY USERNAME " + args[2]);

                        resultUser = DiscordDAO.getByName(DiscordUser.class, args[2]);
                    }

                    talk(event, display(resultUser), privacy);

                }
        });


        actions.get("start").setNext(actions.get("level"));
        actions.get("level").setNext(actions.get("tech"));
        actions.get("tech").setNext(actions.get("goal"));
        actions.get("goal").setNext(actions.get("timezone"));
        actions.get("timezone").setNext(actions.get("age"));
        actions.get("age").setNext(actions.get("country"));
        actions.get("country").setNext(actions.get("update"));
        actions.get("update").setNext(actions.get("view"));
    }


    public void removeMessages(TextChannel ch) {
        ch.getHistoryAfter(0L, 100).queue(new Consumer<MessageHistory>() {
            @Override
            public void accept(MessageHistory messageHistory) {
                if (!messageHistory.isEmpty()) {
                    List<Message> messages = messageHistory.getRetrievedHistory();

                    for (Message msg : messages) {
                        if (!msg.getAuthor().getId().equalsIgnoreCase("584094607676145666")) {
                            System.out.println(msg.getContentRaw());
                            msg.delete().queue();
                        }

                    }
                }
            }
        });

        for (Message msg : channels.get("server-info").getHistory().getRetrievedHistory()) {
            System.out.println(msg.getContentRaw());
            msg.delete().queue();
        }
    }

    public WelcomeBot(Guild server) {
        System.out.println("In constructor");

        javaServer = server;
        System.out.println(javaServer.getName());
        channels.put("server-info", javaServer.getTextChannelById("586250800138027039"));

        System.out.println(javaServer.getName());
    }

    public static void main(String[] args) {
        //Path path = Paths.get("src", "main", "resources", "tokens", "WelcomeBot.token");
        //String answerPath = Main.class.getResource("/resources/tokens/WelcomeBot.token").getPath();
        //Path path = Paths.get("/tokens/WelcomeBot.token");
        String path = Weapon.class.getResource("/tokens/WelcomeBot.token").getPath();

        try  {

   //         System.out.println("answerPath: " + answerPath);

         //   String finalPath = new Watcher().getClass().getClassLoader().getResource(
        //            "resources/tokens/WelcomeBot.token").getPath();
       //     System.out.println("final path: " + finalPath);
//            BufferedReader br0 = new BufferedReader(new InputStreamReader(new Watcher().getClass().getClassLoader().getResourceAsStream(
        //            "resources/tokens/WelcomeBot.token")));
           // System.out.println("br0: " + br0.readLine());

            //System.out.println("path: " + path.toAbsolutePath().toString());
            System.out.println("path: " + path);
           // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            BufferedReader br = new BufferedReader(new InputStreamReader(Weapon.class.getResourceAsStream("/tokens/WelcomeBot.token")));
            String token = br.readLine();
            br.close();

            JDABuilder builder = new JDABuilder(AccountType.BOT);
            builder.setToken(token);
            JDA jda = builder.build();
            jda.awaitReady();

            jda.addEventListener(new WelcomeBot());
        }
        catch (Exception e)  {
            e.printStackTrace();
         //   System.out.println("tried answerPath: " + answerPath);
            //System.out.println("tried: path" + path.toAbsolutePath().toString());
            e.printStackTrace();
        }


    }

    public WelcomeBot() {
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getAuthor().getName().equalsIgnoreCase("WelcomeBot")) {
            return; //ignore this bot
        }

        if (event.getMessage().getContentRaw().contains("!w get avatar"))  {
            String[] args = event.getMessage().getContentRaw().split(" ");
            List<User> users = event.getMessage().getMentionedUsers();

            event.getChannel().sendMessage(users.get(0).getAvatarUrl()).queue();
        }

        Action.Privacy privacy = Action.Privacy.PUBLIC;
        if (event.getMessage().getContentRaw().toLowerCase().contains("private"))  {
            privacy = Action.Privacy.PRIVATE;
        }

        if (event.getChannelType().equals(ChannelType.PRIVATE))  {
            privacy = Action.Privacy.PRIVATE;
        }

      //  if (!event.getChannel().getName().equalsIgnoreCase("server-info")) {
    //        if (!event.getChannelType().equals(ChannelType.PRIVATE)) {
     //           return;  //reply only to DMs or messages inside server-info
    //        }
     //   }

        System.out.println("tag: " + event.getAuthor().getAsTag());
        String input = event.getMessage().getContentRaw();
        String[] args = input.split(" ");

        DiscordUser user;
        if (currentUsers.containsKey(event.getAuthor().getId()))  {
            user = currentUsers.get(event.getAuthor().getId());
        }
        else  {
            user = new DiscordUser();
            user.setName(event.getAuthor().getName());
            user.setId(event.getAuthor().getId());
            user.setTag(event.getAuthor().getAsTag());
            currentUsers.put(user.getId(), user);
        }

        if (actions.containsKey(args[1]))  {
            if (!input.equalsIgnoreCase("!w update") && args[args.length-1].equalsIgnoreCase("update"))  {
                System.out.println("privacy now: " + privacy);

                actions.get(args[1]).perform(input, user, event, Action.Narration.UPDATE, privacy);
                currentUsers.remove(user);
                actions.get("view").perform("!w view me", user, event, Action.Narration.NEXT, privacy);
            } else  {
                actions.get(args[1]).perform(input, user, event, Action.Narration.NEXT, privacy);
            }

        }
        else if (args[1].equalsIgnoreCase("create") && args[2].equalsIgnoreCase("roles")) {
       //     actions.get("error").talk(event);
            updateRoles(event);

        }
    }

    public static void updateRole(MessageReceivedEvent event, Member member)  {
            Guild server = event.getGuild();
            System.out.println("member: " + member.getUser().getName());
            DiscordUser userMember = DiscordDAO.getByName(DiscordUser.class, member.getUser().getName());
            try  {
                List<Role> roles = new ArrayList<>();
                roles.add(event.getGuild().getRolesByName(userMember.getLevel(), false).get(0));

                List<Role> existingMemberRoles = member.getRoles();
                for (Role role: existingMemberRoles)  {
                    if (Level.inList(levels, role.getName()))  {
                        System.out.println("Removing role: " + role);

                        server.getController().removeRolesFromMember(member, role).queue();
                    }
                }

                System.out.println("Adding role: " + roles.get(0));
                server.getController().addRolesToMember(member, roles.get(0)).queue();
            }
            catch (Exception e)  {e.printStackTrace();}
        }

    public static void updateRoles(MessageReceivedEvent event)  {
        Guild server = event.getGuild();
        List<Member> members = server.getMembers();
        for (Member member: members) {
            updateRole(event, member);
        }
    }

    public DiscordMessage display(DiscordUser user)  {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Viewer");

        String message = "";
        for (String key: user.getAnswered().keySet())  {
            message+= "**" + key.substring(0, 1).toUpperCase() + key.substring(1) + "**: ";
            message+=user.getAnswered().get(key).getAnswer();
            message+="\n";
        }
        message+="**Tag: **" + user.getTag() + "\n";

        embed.addField("**@" + user.getName() + "'s answers:**\n", message, false);
        embed.setThumbnail("https://i.redd.it/0vxdnwzz9ei11.jpg");

        return new DiscordMessage(embed);
    }
}

