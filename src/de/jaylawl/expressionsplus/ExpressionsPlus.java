package de.jaylawl.expressionsplus;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;

public class ExpressionsPlus extends JavaPlugin {

    private SkriptAddon addon;

    @Override
    public void onEnable() {
        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            if (!Bukkit.getBukkitVersion().contains("1.12")) {
                getLogger().info("Warning: this plugin was designed for MC 1.12.x");
            }
            addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("de.jaylawl.expressionsplus", "elements");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!Skript.classExists("com.destroystokyo.paper.PaperConfig")) {
                getLogger().info("Couldn't detect PaperSpigot");
                getLogger().info("Some expressions might be unavailable");
            }
            getLogger().info("Successfully enabled");
        } else {
            getLogger().info("The dependency Skript is missing: disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
    }

}