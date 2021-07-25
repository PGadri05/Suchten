package de.pgspeed.suchten.servermessages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage("§aDer Spieler:  §4"+ player.getPlayerListName()+"§aist nun unter uns! :)");
    }
    
}
