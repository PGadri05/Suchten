package de.pgspeed.suchten.position.commands;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.pgspeed.suchten.config.Config;
import de.pgspeed.suchten.main.Main;

public class PositionCommand implements CommandExecutor, TabCompleter{
    private Config positionConfig = new Config("positionConfig.yml", Main.getInstance().getDataFolder());
    FileConfiguration config = positionConfig.getFileConfiguration();

    String[] args0complete = {
        "reset",
        "create",
        "list",
        "tp",
        "delete"
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            String worldName = player.getWorld().getName();
            List<String> posNames = config.getStringList(worldName+".names");
            switch(args[0].toLowerCase()){
                default:
                    break;
                case "create":{
                    if(args.length != 2){
                        SendUsage(sender);
                        break;
                    }
                    String posName = args[1].toLowerCase();
                    if(posNames.contains(posName)){
                        player.sendMessage("§cEs gibt diese Position bereits!");
                        break;
                    }
                    posNames.add(posName);
                    config.set(worldName+".names", posNames);
                    
                    Integer x = (int) player.getLocation().getX();
                    Integer y = (int) player.getLocation().getY();
                    Integer z = (int) player.getLocation().getZ();

                    config.set(worldName+".positions."+posName+".x", x);
                    config.set(worldName+".positions."+posName+".y", y);
                    config.set(worldName+".positions."+posName+".z", z);

                    sender.sendMessage("§6Die Position: "+ posName + " " + x +" " + y + " " + z + "§a wurde erfolgreich erstellt!");
                    break;
                }
                case "delete":{
                    if(args.length != 2){
                        SendUsage(sender);
                        break;
                    }
                    String posName = args[1].toLowerCase();
                    if(!posNames.contains(posName)){
                        player.sendMessage("§cDie Position wurde nicht gefunden");
                        break;
                    }
                    posNames.remove(posName);
                    config.set(worldName+".names", posNames);

                    config.set(worldName+".positions."+posName, null);                        
                    break;
                }
                case "list":{
                    player.sendMessage("§aIn "+worldName+" wurden "+posNames.size()+" Positionen gesetzt.");
                    for(int i = 0; i < posNames.size(); i++){
                        String x = config.getString(worldName+".positions."+posNames.get(i)+".x");
                        String y = config.getString(worldName+".positions."+posNames.get(i)+".y");
                        String z = config.getString(worldName+".positions."+posNames.get(i)+".z");
                        player.sendMessage("§e"+posNames.get(i)+"§5 -> X: §6"+x+" §5Y: §6"+y+" §5Z: §6"+z);   
                    }
                    break;
                }
                case "tp":{
                    if(!player.hasPermission("speedrun.position")){
                        player.sendMessage("§cKauf dir die Rechte bei Adi");
                        break;
                    }
                    if(args.length != 2){
                        SendUsage(sender);
                        break;
                    }
                    String posName = args[1].toLowerCase();
                    if(posNames.contains(posName)){
                        player.sendMessage("§aDu wirst teleportiert");
                    }else{
                        player.sendMessage("§cPosition wurde nicht gefunden!");
                        break;
                    }
                    Location tpLocation = new Location(Bukkit.getWorld(worldName), config.getInt(worldName+".positions."+posName+".x"), config.getInt(worldName+".positions."+posName+".y"), config.getInt(worldName+".positions."+posName+".z"));
                    player.teleport(tpLocation);
                    break;
                }
                case "reset":{
                    if(!player.hasPermission("speedrun.position")){
                        player.sendMessage("§c Kauf dir die Rechte bei Adi");
                        break;
                    }
                    config.set(worldName, null);
                    player.sendMessage("§aDie Position dieser Welt wurden resetet");
                    break;
                }
            }
            positionConfig.Save();
            
        }else{
            sender.sendMessage("§c Du Musst den Command als Spieler Benutzen");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    List<String> tabcompleteList = new ArrayList<String>();
    Player player = (Player)sender;
        switch(args.length){
            case 1:
                for(int i = 0; i < args0complete.length; i++){
                    tabcompleteList.add(args0complete[i]);
                }
                return tabcompleteList;
            case 2:
                List<String> args1complete = config.getStringList(player.getWorld().getName()+".names");
                return args1complete;
            default:
                return tabcompleteList;
        }
    }

    private void SendUsage(CommandSender sender){
        sender.sendMessage("§cBitte benutze: /position create <Name>, /position delete <Name>, /position list, /position tp <Name>, /position reset ! §a:)");
    }
}
