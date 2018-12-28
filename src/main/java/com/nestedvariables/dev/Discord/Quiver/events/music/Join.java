package com.nestedvariables.dev.Discord.Quiver.events.music;

import com.nestedvariables.dev.Discord.Quiver.Prefix;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Prefix.getPrefix(event) + "join")) {

            event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
            event.getChannel()
                    .sendMessage("Joining " + event.getMember().getVoiceState().getChannel().getName().toString())
                    .queue();
        }

    }

}