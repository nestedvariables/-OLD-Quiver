package com.nestedvariables.dev.Discord.Quiver.events.owner;

import com.nestedvariables.dev.Discord.Quiver.Checks;
import com.nestedvariables.dev.Discord.Quiver.GuildData;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Unblacklist extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(GuildData.getPrefix(event.getGuild()) + "unblacklist")) {
            if(Checks.isBotOwner(event)) {
                
            }
        }
    }
}