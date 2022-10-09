package io.github.bytecode.bytecodediscordbot;

import com.google.common.io.Files;
import io.github.bytecode.bytecodediscordbot.commands.CommandManager;
import io.github.bytecode.bytecodediscordbot.event.EventManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;


public class Bot {
    private JDA jda;
    private EventManager eventManager;
    private CommandManager commandManager;
    private static Bot instance;

    public Bot(String token){
        instance = this;
        try{
            JDABuilder builder = JDABuilder.createDefault(token);

            for (GatewayIntent intent : GatewayIntent.values()) {
                builder.enableIntents(intent);
            }

            this.commandManager = new CommandManager();

            this.eventManager = new EventManager();
            builder.addEventListeners(eventManager);

            this.jda = builder.build();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JDA getJda() {
        return jda;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public static Bot getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        try {

            String token = readFile("token.txt");
            new Bot(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        return FileUtils.readFileToString(file);
    }
}
