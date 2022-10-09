package io.github.bytecode.bytecodediscordbot.commands;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Arrays;
import java.util.List;

public interface ICommand {
    String getName();

    String getDescription();

    default List<OptionData> getOptions() {
        return Arrays.asList();
    }

    void execute(SlashCommandContext ctx);
}
