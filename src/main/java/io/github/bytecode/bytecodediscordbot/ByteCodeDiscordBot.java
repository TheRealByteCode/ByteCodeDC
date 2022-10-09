package io.github.bytecode.bytecodediscordbot;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public final class ByteCodeDiscordBot extends JavaPlugin {
    @Override
    public void onEnable() {
        String token = getConfig().getString("token");

        if(token == null || token.length() == 0){
            getLogger().severe("Token not found in config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new Bot(token);

        getLogger().info("ByteCodeDiscordBot has been enabled!");
    }
}
