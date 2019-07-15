package javastudybuddies.discordbots.messagemover;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.function.Consumer;

public class MessageMoverBot extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        Guild guild = event.getGuild();

        if (event.getMessage().getContentRaw().startsWith("!move all to"))  {
            System.out.println("!move all to");
            TextChannel toChannel = guild.getTextChannelsByName(args[3], true).get(0);
            moveMessages(0L, 100, event.getTextChannel(), toChannel);
        }
    }

    public void moveMessages(long messageId, int limit, TextChannel fromChannel, TextChannel toChannel)  {
        fromChannel.getHistoryAfter(messageId, limit).queue(new Consumer<MessageHistory>() {
            @Override
            public void accept(MessageHistory messageHistory) {
                if (!messageHistory.isEmpty()) {
                    System.out.println(messageHistory.size());
                    List<Message> messages  = messageHistory.getRetrievedHistory();

                    int count=0;
                    long msgId = 0L;
                    for (Message msg : messages) {
                        if (msg.getContentDisplay().contains("!"))  {
                            continue;
                        }

                        System.out.println(msg.getContentRaw());
                        String toSend = "@" + msg.getAuthor().getAsTag() + ": \n";
                        toSend += msg.getContentDisplay();
                        toChannel.sendMessage(toSend).queue();

                        msgId = msg.getIdLong();
                        System.out.println("msgId: " + msgId);
                    }

                    moveMessages(msgId, 100, fromChannel, toChannel);
                }
            }
        });
    }

}
