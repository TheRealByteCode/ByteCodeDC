package io.github.bytecode.bytecodediscordbot.commands.impl;

import io.github.bytecode.bytecodediscordbot.commands.ICommand;
import io.github.bytecode.bytecodediscordbot.commands.SlashCommandContext;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ICommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Löscht eine bestimmte Anzahl an Nachrichten aus dem Chat";
    }

    @Override
    public List<OptionData> getOptions() {
        return Arrays.asList(
                new OptionData(OptionType.INTEGER, "anzahl", "Die Anzahl an Nachrichten die gelöscht werden sollen", true)
        );
    }

    @Override
    public void execute(SlashCommandContext ctx) {
        int amount = ctx.getEvent().getOption("anzahl").getAsInt();

        try {
            ctx.getEvent().getChannel().getIterableHistory().takeAsync(amount).thenAccept(messages -> {
                ctx.getEvent().getChannel().purgeMessages(messages);
            });

            ctx.getEvent().getHook().sendMessage("Die Nachrichten wurden erfolgreich gelöscht!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        } catch (Exception e) {

        }
    }
}
