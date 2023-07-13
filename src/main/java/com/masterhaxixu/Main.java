package com.masterhaxixu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getLogger().info("Plugin enabled successfully!");
        getLogger().info("Coded By Master Haxixu#1602");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof Snowball) {
            Snowball snowball = (Snowball) projectile;

            if (snowball.getShooter() instanceof Snowman) {
                int totalSnowballCount = calculateTotalSnowballCount();
                if (totalSnowballCount > config.getInt("snowballLimit")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill @e[type=snowball]");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill @e[type=snow_golem]");
                }
            }
        }
    }

    private int calculateTotalSnowballCount() {
        int count = 0;

        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
            if (entity instanceof Snowball) {
                count++;
            }
        }

        return count;
    }
}
