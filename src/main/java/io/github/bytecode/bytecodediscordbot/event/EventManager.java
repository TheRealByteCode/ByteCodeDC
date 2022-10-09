package io.github.bytecode.bytecodediscordbot.event;

import io.github.bytecode.bytecodediscordbot.Bot;
import io.github.bytecode.bytecodediscordbot.commands.ICommand;
import io.github.bytecode.bytecodediscordbot.utils.ID;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EventManager extends ListenerAdapter {
    private HashMap<Member, Integer> verifySolutionsMap;

    public EventManager() {
        this.verifySolutionsMap = new HashMap<>();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<ICommand> commands = Bot.getInstance().getCommandManager().getCommands();
        Guild guild = Bot.getInstance().getJda().getGuildById(ID.GUILD_ID.getId());

        commands.forEach(command -> {
            guild.upsertCommand(command.getName(), command.getDescription()).addOptions(command.getOptions()).queue();
        });

        guild.updateCommands().queueAfter(1, TimeUnit.SECONDS);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Bot.getInstance().getCommandManager().handle(event);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String id = event.getButton().getId();

        switch (id) {
            case "button_verify":
                Random r = new Random();
                int n1 = r.nextInt(50) + 1;
                int n2 = r.nextInt(50) + 1;
                int solution = n1 + n2;
                verifySolutionsMap.put(event.getMember(), solution);
                String math = "" + n1 + "+" + n2;

                TextInput verifyInput = TextInput.create("input_verify", math, TextInputStyle.SHORT).build();
                Modal verifyModal = Modal.create("modal_verify", "Verifizieren").addActionRows(ActionRow.of(verifyInput)).build();

                event.replyModal(verifyModal).queue();
                break;
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String modalId = event.getModalId();

        switch (modalId) {
            case "modal_verify":
                String input = event.getValue("input_verify").getAsString();

                try{
                    int answer = Integer.parseInt(input);

                    if(verifySolutionsMap.get(event.getMember()) == answer) {
                        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ID.MEMBER_ROLE.getId())).queue();
                        verifySolutionsMap.remove(event.getMember());
                        event.deferEdit().queue();
                    } else {
                        event.reply("Deine Antwort war leider falsch!").setEphemeral(true).queue();
                    }
                } catch (NumberFormatException e){
                    event.reply("Bitte gebe eine Zahl ein!").setEphemeral(true).queue();
                    return;
                }
                break;
        }
    }
}
