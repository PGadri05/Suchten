package de.pgspeed.suchten.challenges.runaway;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.pgspeed.suchten.main.Main;

public class Runaway {

    boolean isRunning;

    int startX;
    int startY;
    int startZ;

    Player startPlayer;

    RunawayDirection direction;

   //Map<Player, Integer> playerDistances = new HashMap<Player, Integer>();

    public Runaway(){
        isRunning = false;
    }

    public void Start(Player player, RunawayDirection runawayDirection){
        if(isRunning){
            player.sendMessage("§cEs wurde bereits gestartet");
            return;
        }
        this.direction = runawayDirection;
        startX = (int)player.getLocation().getX();
        startY = (int)player.getLocation().getY();
        startZ = (int)player.getLocation().getZ();


        isRunning = true;

        Location tpLocation = new Location(player.getWorld(), startX, startY, startZ);
        for (Player tpPlayer : Bukkit.getServer().getOnlinePlayers()) {
            if(player.getWorld() == tpPlayer.getWorld()){
                tpPlayer.teleport(tpLocation);
                tpPlayer.sendMessage("Lauf um dein Leben!!!");
            }
            
        }

        player.sendMessage("§aGestartet. LOS!");
        startRunawayTick();

    }
    public void Stop(Player player){
        if(!isRunning){
            player.sendMessage("§cDas weglaufen war nicht aktiv");
            return;
        }


        isRunning = false;

        player.sendMessage("§aGestopt. Halt!");
    }
    private void startRunawayTick(){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(!isRunning){
                    return;
                }
                sidebar();
            }
            
        }.runTaskTimer(Main.getInstance(), 20, 20);

    }
    private void sidebar(){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("abcd", "abcd", "abcd");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "Runaway");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            objective.getScore(player.getName()).setScore(getPlayerStartDistance(player));
        }
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }

    private int getPlayerStartDistance(Player player){
        int distance = 0;
        switch(direction){
            case X:
            distance = (int) (player.getLocation().getX() - startX);
                break;
            case Y:
            distance = (int) (player.getLocation().getY() - startY);            
                break;
            case Z:
            distance = (int) (player.getLocation().getZ() - startZ);
                break;
            default:
                break;
            
        }
        return distance;

    }

}
