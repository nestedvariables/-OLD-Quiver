package com.nestedvariables.dev.Discord.Quiver.events.owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.nestedvariables.dev.Discord.Quiver.Bools;
import com.nestedvariables.dev.Discord.Quiver.Info;
import com.nestedvariables.dev.Discord.Quiver.SQLDriver;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BlacklistMember extends ListenerAdapter {

    String reason = "";

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].equalsIgnoreCase(Info.PREFIX + "blacklist")) {

            if (Bools.isBotOwner(event)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
                Date date = new Date();
                if (args.length < 2) {
                    EmbedBuilder nullUser = new EmbedBuilder();

                    nullUser.setDescription(":white_medium_small_square: " + event.getMember().getAsMention()
                            + ", you didn't specify which member to blacklist!");
                    nullUser.setColor(Info.ERROR_RED);
                    nullUser.setFooter("Quiver Undefined Member", Info.LOGO);

                    event.getChannel().sendMessage(nullUser.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                } else {
                    try {

                        for (int i = 2; i < args.length; i++) {
                            reason = reason + args[i] + " ";
                        }

                        Connection conn = SQLDriver.getConn();
                        Statement stmt = conn.createStatement();

                        Member blacklistedMember = event.getMessage().getMentionedMembers().get(0);

                        stmt.execute(
                                "INSERT INTO `blacklist`(`discord_id`, `discord_username`, `reason`, `blacklister`, `blacklist_time`)"
                                        + "VALUES('" + blacklistedMember.getUser().getId().toString() + "','"
                                        + blacklistedMember.getUser().getName().toString() + "#"
                                        + blacklistedMember.getUser().getDiscriminator().toString() + "','" + reason
                                        + "','" + event.getMember().getUser().getName().toString() + "#"
                                        + event.getMember().getUser().getDiscriminator().toString() + "','"
                                        + dateFormat.format(date) + "')");

                        EmbedBuilder eb = new EmbedBuilder();

                        eb.setTitle("Blacklisted!");
                        eb.setColor(Info.ERROR_RED);
                        eb.setThumbnail(blacklistedMember.getUser().getAvatarUrl());
                        eb.setDescription("Blacklisted " + blacklistedMember + " from using Quiver");
                        eb.addField("Reason", reason, false);
                        eb.setFooter("Quiver Blacklisted Member" , Info.LOGO);

                        event.getMember().getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        });
                        conn.close();
                        reason = "";
                    } catch (SQLException sqle) {
                        event.getJDA().getGuildById("488137783127572491").getTextChannelById("517756124846358529")
                                .sendMessage(event.getJDA().getGuildById("488137783127572491")
                                        .getRoleById("489269871306080257").getAsMention()
                                        + "\n A blacklist command failed\n Reason: `" + sqle.toString() + "`")
                                .queue();
                    }
                }
            } else {
                EmbedBuilder nullUser = new EmbedBuilder();

                nullUser.setDescription(event.getMember().getAsMention()
                        + "You can't use that command as you are not one of my owners: ExZiByte#9472 or Tech#9627");
                nullUser.setColor(Info.ERROR_RED);
                nullUser.setFooter("Quiver Not Owner Error", Info.LOGO);

                event.getChannel().sendMessage(nullUser.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                });
            }

        }
    }
}