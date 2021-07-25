package de.pgspeed.suchten.servermessages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener{

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("§cDer Spieler:  §a"+player.getPlayerListName()+"§c hat uns leider verlassen!");
    }
    
}
