package io.github.bytecode.bytecodediscordbot.commands.impl;

import io.github.bytecode.bytecodediscordbot.commands.ICommand;
import io.github.bytecode.bytecodediscordbot.commands.SlashCommandContext;
import io.github.bytecode.bytecodediscordbot.utils.ID;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MessageCommand implements ICommand {
    @Override
    public String getName() {
        return "message";
    }

    @Override
    public String getDescription() {
        return "Sendet spezielle Nachrichten, mit speziellen Funktionen";
    }

    @Override
    public List<OptionData> getOptions() {
        return Arrays.asList(
                new OptionData(OptionType.STRING, "key", "Die Nachricht, die gesendet werden soll", true)
        );

    }

    @Override
    public void execute(SlashCommandContext ctx) {
        if (!ctx.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            ctx.getHook().sendMessage("Du hast keine Berechtigung, diesen Befehl auszuführen!").queue();
            return;
        }

        String key = ctx.getEvent().getOption("key").getAsString();

        EmbedBuilder successBuilder = new EmbedBuilder();
        successBuilder.setColor(Color.GREEN);
        successBuilder.setTitle("Nachricht gesendet");
        successBuilder.setDescription("Die Nachricht wurde erfolgreich gesendet!");

        EmbedBuilder builder = new EmbedBuilder();

        switch (key){
            case "verify":
                builder.setColor(Color.GREEN);
                builder.setTitle("Verifizieren");
                builder.setDescription("Um den Server vor Bot angriffen und ähnlichem zu schützen, musst du dich erst verifizieren. Drücke dafür bitte unten auf den Button, und löse anschließend eine kleine Rechenaufgabe.");

                Button successButton = Button.success("button_verify", "Verifizieren");

                TextChannel verifyChannel = ctx.getGuild().getTextChannelById(ID.VERIFY_CHANNEL.getId());
                verifyChannel.sendMessageEmbeds(builder.build()).setActionRow(successButton).queue();

                ctx.getHook().sendMessageEmbeds(successBuilder.build()).queue();
                break;
            default:
                successBuilder.setTitle("Error");
                successBuilder.setColor(Color.RED);
                successBuilder.setDescription("Die Nachricht konnte nicht gesendet werden, da sie nicht existiert!");

                ctx.getHook().sendMessageEmbeds(successBuilder.build()).queue();
                break;
        }
    }
}
