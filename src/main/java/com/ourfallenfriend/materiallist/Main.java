package com.ourfallenfriend.materiallist;

import com.ourfallenfriend.materiallist.commands.MaterialListCommand;
import com.ourfallenfriend.materiallist.events.OnBlockBreakEvent;
import com.ourfallenfriend.materiallist.events.OnBlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    static Main instance;

    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, "Plugin Successfully Enabled!");
        instance = this;
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Plugin Successfully Disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("MaterialList")).setExecutor(new MaterialListCommand());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnBlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlaceEvent(), this);
    }
}
