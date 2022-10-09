package io.github.bytecode.bytecodediscordbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<ICommand> commands = new ArrayList<>();

    public CommandManager() {

    }

    private void addCommand(ICommand command) {

        commands.forEach(cmd -> {
            if(command.getName().equalsIgnoreCase(cmd.getName())) {
                throw new IllegalArgumentException("Command with name " + command.getName() + " already exists!");
            }
        });

        this.commands.add(command);
    }

    public ICommand getCommand(String name) {
        for(ICommand command : this.commands) {
            if(command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public List<ICommand> getCommands() {
        return this.commands;
    }

    public void handle(SlashCommandInteractionEvent event) {
        event.deferReply();
        ICommand command = this.getCommand(event.getName());

        if(command == null) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.RED);
            builder.setTitle("Error");
            builder.setDescription("Der Command wurde nicht gefunden!");

            event.getHook().sendMessageEmbeds(builder.build()).queue();
            return;
        }

        SlashCommandContext context = new SlashCommandContext(event);
        command.execute(context);
    }


}
