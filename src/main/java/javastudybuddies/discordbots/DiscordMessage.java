package javastudybuddies.discordbots;

import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class DiscordMessage {
        private EmbedBuilder embed;
        private String text;

        public DiscordMessage(EmbedBuilder embed)  {
            this.embed = embed;
            this.embed.setColor(new Color(54, 57, 63));
        }

        public DiscordMessage(String text)  {
            this.text = text;
        }

        public DiscordMessage(EmbedBuilder embed, String text)  {
            this.embed = embed;
            this.text = text;
        }

        //getters
        public EmbedBuilder getEmbed()  {return embed;}
        public String getText()  {return text;}

        //setters
        public void setEmbed(EmbedBuilder embed)  {this.embed = embed;}
        public void setText(String text)  {this.text = text;}
}
