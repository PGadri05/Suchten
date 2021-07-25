package de.pgspeed.suchten.timer;

import de.pgspeed.suchten.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Timer {

    private boolean running;
    private int time;

    //settings
    private boolean deathStop;

    public Timer(boolean running, int time){
        this.running = running;
        this.time = time;
        deathStop = false;

        run();
    }

    private void run(){
        new BukkitRunnable(){

            @Override
            public void run() {
                sendActionBar();

                if(!isRunning()){
                    return;
                }

                setTime(getTime() + 1);
                
            }
            
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public String getTextTime(){
        //Time in Secounds, Minutes and hours
        int secounds; int minutes =0; int hours = 0; int days = 0;
        String Tsecounds; String Tminutes; String Thours; String Tdays;
        int allHours;

        String finishedText = "";
        
        secounds = time % 60;

        days = time / (3600 *24);

        allHours = time / 3600;
        hours = time / 3600 - days * 24;

        minutes = time / 60 - allHours * 60;
      

        //Make time pairs of two
        if(days < 10){
            Tdays = "0"+days;
        }else{
            Tdays = "";
        }
        if(hours < 10){
            Thours = "0"+hours;
        }else{
            Thours = hours+"";
        }
        if(minutes < 10){
            Tminutes = "0"+minutes;
        }else{
            Tminutes = minutes+"";
        }
        if(secounds < 10){
            Tsecounds = "0"+secounds;
        }else{
            Tsecounds = secounds+"";
        }

        if(days == 0){
            if(hours == 0){
                finishedText = Tminutes+":"+Tsecounds;
            }else{
                finishedText = Thours+":"+ Tminutes+":"+Tsecounds;
            }
            
        }else{
            finishedText = Tdays+ ":" +Thours+":"+ Tminutes+":"+Tsecounds;
        }




        return finishedText;

    }

    public boolean isRunning() {
        return running;
    }
    public int getTime() {
        return time;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public boolean isDeathStop(){
        return deathStop;
    }
    public void setDeathStop(boolean deathStop) {
        this.deathStop = deathStop;
    }

    public void sendActionBar(){
        for (Player player : Bukkit.getOnlinePlayers() ) {
            if(!isRunning()){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + ChatColor.BOLD + "Timer pausiert"));
                continue;
            }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + ChatColor.BOLD + getTextTime()));
        }
    }

}
