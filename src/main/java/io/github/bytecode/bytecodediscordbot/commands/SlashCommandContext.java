package io.github.bytecode.bytecodediscordbot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class SlashCommandContext {
    private SlashCommandInteractionEvent event;

    public SlashCommandContext(SlashCommandInteractionEvent event){
        this.event = event;
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    private Member getMember() {
        return event.getMember();
    }

    private MessageChannel getChannel() {
        return event.getChannel();
    }

    private InteractionHook getHook() {
        return event.getHook();
    }
}
