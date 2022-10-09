package io.github.bytecode.bytecodediscordbot.event;

import io.github.bytecode.bytecodediscordbot.Bot;
import io.github.bytecode.bytecodediscordbot.commands.ICommand;
import io.github.bytecode.bytecodediscordbot.utils.ID;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventManager extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<ICommand> commands = Bot.getInstance().getCommandManager().getCommands();
        Guild guild = Bot.getInstance().getJda().getGuildById(ID.GUILD_ID.getId());

        commands.forEach(command -> {
            guild.upsertCommand(command.getName(), command.getDescription()).addOptions(command.getOptions()).queue();
        });

        guild.updateCommands().queueAfter(1, TimeUnit.SECONDS);

    }
}
