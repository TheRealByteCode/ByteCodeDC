package io.github.bytecode.bytecodediscordbot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByteCodeDiscordBot extends JavaPlugin {

    private static ByteCodeDiscordBot instance;
    private Bot botInstance;

    @Override
    public void onEnable() {
        instance = this;

        String token = getConfig().getString("token");

        if(token == null || token.length() == 0){
            getLogger().severe("Token not found in config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        botInstance = new Bot(token);

        getLogger().info("ByteCodeDiscordBot has been enabled!");
    }
}
