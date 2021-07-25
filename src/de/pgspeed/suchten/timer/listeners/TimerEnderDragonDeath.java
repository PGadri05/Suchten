package de.pgspeed.suchten.timer.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import de.pgspeed.suchten.main.Main;
import de.pgspeed.suchten.timer.Timer;

public class TimerEnderDragonDeath implements Listener {
    
    @EventHandler
    public void onEnderdragonDeath(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
            if(entity.getType() == EntityType.ENDER_DRAGON){
                Timer timer = Main.getInstance().getTimer();
                timer.setRunning(false);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("§aIn §e"+timer.getTextTime()+"§a geschafft! JUHU");
                    player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, SoundCategory.MASTER, 10, 10);
                }
            }
    }
}
