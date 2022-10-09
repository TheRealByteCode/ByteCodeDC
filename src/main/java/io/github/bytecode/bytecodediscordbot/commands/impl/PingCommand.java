package io.github.bytecode.bytecodediscordbot.commands.impl;

import io.github.bytecode.bytecodediscordbot.Bot;
import io.github.bytecode.bytecodediscordbot.commands.ICommand;
import io.github.bytecode.bytecodediscordbot.commands.SlashCommandContext;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class PingCommand implements ICommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Ist der Bot noch online? Finde es heraus!";
    }

    @Override
    public void execute(SlashCommandContext ctx) {
        long gatewayPing = Bot.getInstance().getJda().getGatewayPing();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Pong!");
        builder.setDescription("Gateway Ping: " + gatewayPing + "ms");
        builder.setColor(Color.GREEN);

        ctx.getHook().sendMessageEmbeds(builder.build()).queue();
    }
}
