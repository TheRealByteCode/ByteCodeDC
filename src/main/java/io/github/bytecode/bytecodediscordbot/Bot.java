package io.github.bytecode.bytecodediscordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {
    private JDA jda;

    public Bot(String token){
        try{
            JDABuilder builder = JDABuilder.createDefault(token);

            for (GatewayIntent intent : GatewayIntent.values()) {
                builder.enableIntents(intent);
            }

            this.jda = builder.build();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
