package com.nestedvariables.dev.Discord.Quiver.events.music;

import com.nestedvariables.dev.Discord.Quiver.Prefix;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RemoveDupes extends ListenerAdapter {
    
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Prefix.getPrefix(event))){
            
        }


    }
   

}