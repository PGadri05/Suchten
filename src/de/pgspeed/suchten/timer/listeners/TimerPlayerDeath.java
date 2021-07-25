package de.pgspeed.suchten.timer.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.pgspeed.suchten.main.Main;
import de.pgspeed.suchten.timer.Timer;

public class TimerPlayerDeath implements Listener{
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        Timer timer = Main.getInstance().getTimer();
        
        if(!timer.isDeathStop()){
            return;
        }
        
        timer.setRunning(false);
        String t = timer.getTextTime();
        timer.setTime(0);

        for (Player player2 : Bukkit.getOnlinePlayers()) {
            player2.setGameMode(GameMode.SPECTATOR);
            player2.playSound(player2.getLocation(), Sound.BLOCK_GLASS_BREAK , SoundCategory.MASTER, 10, 10);
            player2.sendMessage("§cDer Run ist nach: §e"+t+"§c aufgrund von: §7"+player.getName()+"§c gescheitert! Schande über ihn");
        }

    }
}
