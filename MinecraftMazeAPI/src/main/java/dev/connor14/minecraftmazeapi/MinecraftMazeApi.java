package dev.connor14.minecraftmazeapi;

import dev.connor14.minecraftmazeapi.commands.CommandMazeGenerate;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftMazeApi extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Welcome to the Minecraft Maze API!");
        this.getCommand("maze").setExecutor(new CommandMazeGenerate());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
