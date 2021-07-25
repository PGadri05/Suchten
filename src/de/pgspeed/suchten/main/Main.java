package de.pgspeed.suchten.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.pgspeed.suchten.position.listener.PositionCommand;
import de.pgspeed.suchten.timer.Timer;
import de.pgspeed.suchten.timer.commands.TimerCommand;
import de.pgspeed.suchten.timer.listeners.TimerEnderDragonDeath;
import de.pgspeed.suchten.timer.listeners.TimerPlayerDeath;

public class Main extends JavaPlugin{
    
    //Instance of the Main
    private static Main instance;

    //Timer
    private Timer timer;

    @Override
    public void onLoad() {
        
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        //Position
        getCommand("position").setExecutor(new PositionCommand());
        getCommand("position").setTabCompleter(new PositionCommand());

        //Timer
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("timer").setTabCompleter(new TimerCommand());
        pluginManager.registerEvents(new TimerEnderDragonDeath(), this);
        pluginManager.registerEvents(new TimerPlayerDeath(), this);


    }

    @Override
    public void onDisable() {
        
    }


    //Get & Set
    //Main getter
    public static Main getInstance() {
        return instance;
    }
    //Timer
    public Timer getTimer() {
        return timer;
    }

}
