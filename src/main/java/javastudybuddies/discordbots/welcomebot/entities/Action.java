package javastudybuddies.discordbots.welcomebot.entities;

import javastudybuddies.discordbots.DiscordDAO;
import javastudybuddies.discordbots.entities.DiscordMessage;
import javastudybuddies.discordbots.entities.DiscordUser;
import javastudybuddies.discordbots.welcomebot.WelcomeBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Action<V> {


        public enum Narration {
                NEXT, UPDATE;
        }

        public enum Privacy  {
                PUBLIC, PRIVATE;
        }

        protected String errorMessage;

        protected List<DiscordMessage> messages;
        protected Action next;

        protected DiscordUser user;

        {
                messages = new ArrayList<>();
        }

        Narration narration;
        public void setNarration(Narration narration)  {this.narration = narration;}

        public Action()  {
                setup();
        }

        public abstract void setup();

        public void addMessage(DiscordMessage message)  {
                messages.add(message);
        }

        //getters
        public String getErrorMessage()  {return errorMessage;}
        public Action getNext()  {return next;}
        public List<DiscordMessage> getMessages()  {return messages;}

        //setters
        public void setErrorMessage(String errorMessage)  {this.errorMessage = errorMessage;}
        public void setNext(Action next)  {this.next = next;}
        public void setMessages(List<DiscordMessage> embeds)  {this.messages = embeds;}

        public boolean checkSyntax(String input)  {
                String[] args = input.split(" ");

                if (!args[0].equalsIgnoreCase("!welcome") && !args[0].equalsIgnoreCase("!w"))  {
                        return false;
                }

                return true;
        };

        public abstract boolean check(MessageReceivedEvent event, String input, DiscordUser user, Action.Privacy privacy);


        public void process(MessageReceivedEvent event, String input, DiscordUser user, Narration type, Action.Privacy privacy)  {

        }

        public void talk(MessageReceivedEvent event, Action.Privacy privacy)  {
                talk(event, messages, privacy);
        };


        public void talk(MessageReceivedEvent event, String message, Action.Privacy privacy)  {
                talk(event, new DiscordMessage(message), privacy);
        }

        public void talk(MessageReceivedEvent event, List<DiscordMessage> messages, Action.Privacy privacy)  {
                for (DiscordMessage message: messages)  {
                        talk(event, message, privacy);
                }
        }

        public void talk(MessageReceivedEvent event, DiscordMessage message, Privacy privacy)  {
                System.out.println("inside talk: " + privacy);

                if (privacy==Privacy.PUBLIC)  {
                                if (message.getEmbed()!=null)  {
                                        event.getChannel().sendMessage(message.getEmbed().build()).queue();
                                }
                                if (message.getText()!=null)  {
                                        event.getChannel().sendMessage(message.getText()).queue();
                                }
                }
                else {
                        event.getMessage().getAuthor().openPrivateChannel().queue(new Consumer<PrivateChannel>() {
                                @Override
                                public void accept(PrivateChannel channel) {
                                        if (message.getEmbed() != null) {
                                                channel.sendMessage(message.getEmbed().build()).queue();
                                        }
                                        if (message.getText() != null && !message.getText().equalsIgnoreCase("")) {
                                                channel.sendMessage(message.getText()).queue();
                                        }
                                }
                        });
                }
        }

        public void talk(MessageReceivedEvent event, EmbedBuilder builder, Action.Privacy privacy)  {
                        talk(event, new DiscordMessage(builder), privacy);
        }

        public void perform(String input, DiscordUser user, MessageReceivedEvent event)  {
                perform(input, user, event, Narration.NEXT, Privacy.PRIVATE);
        }

        public void perform(String input, DiscordUser user, MessageReceivedEvent event, Narration narration, Privacy privacy)  {
                if (check(event, input, user, privacy))  {
                        process(event, input, user, narration, privacy);

                        if (narration ==Narration.UPDATE)  {
                                DiscordDAO.insert(user);
                                System.out.println("Inside update");

                                try {
                                        WelcomeBot.updateRole(event, event.getMember());
                                }
                                catch (Exception e)  {e.printStackTrace();}
                        }  else  {
                                if (next!=null)  {
                                        next.talk(event, privacy);
                                }
                        }

                }
        }
}
