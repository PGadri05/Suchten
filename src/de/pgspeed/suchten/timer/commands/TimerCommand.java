package de.pgspeed.suchten.timer.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.pgspeed.suchten.main.Main;
import de.pgspeed.suchten.timer.Timer;

public class TimerCommand implements CommandExecutor, TabCompleter{
    
    String[] args0Complete ={
        "resume",
        "pause",
        "time",
        "reset",
        "settings"
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("suchten.timer")){
                Timer timer = Main.getInstance().getTimer();
                
                switch (args[0].toLowerCase()){
                    case "resume":
                        if(timer.isRunning()){
                            sender.sendMessage("§cDer Timer läuft bereits.");
                            break;
                        }
                        timer.setRunning(true);
                        sender.sendMessage("§cDer Timer wurde gestartet.");
                        break;
                    case "pause":
                        if(!timer.isRunning()){
                            sender.sendMessage("Der Timer wurde bereits angehalten!");
                            break;
                        }
                        timer.setRunning(false);
                        sender.sendMessage("Der Timer wurde pausiert.");
                        break;
                    case "time":
                        if(args.length != 2){
                            sendUsage(sender);
                            break;
                        }
                        try{
                            timer.setTime(Integer.parseInt(args[1]));
                            sender.sendMessage("Der Timer wurde geändert!");
                        }catch(NumberFormatException ex){
                            sender.sendMessage("§cDein Parameter 2 muss eine Zahl sein!");
                        }                        
                        break;
                    case "reset":
                        timer.setRunning(false);
                        timer.setTime(0);
                        sender.sendMessage("Der Timer wurde zurückgesetzt.");
                        break;
                    case "settings":
                        if(args.length < 2){
                            sendUsage(sender);
                            break;
                        }
                        switch(args[1].toLowerCase()){
                            default:
                                sendUsage(sender);
                                break;
                            case "deathstop":
                                if(args.length != 3){
                                    sendUsage(sender);
                                    break;
                                }
                                if(args[2].toLowerCase().equals("true")){
                                    timer.setDeathStop(true);
                                    player.sendMessage("§aDeathStop wurde aktiviert");
                                }else if(args[2].toLowerCase().equals("false")){
                                    timer.setDeathStop(false);
                                    player.sendMessage("§aDeathStop wurde deaktiviert");
                                }else{
                                    sendUsage(sender);
                                    break;
                                }
                                break;
                        }

                        break;
                    default:
                        sendUsage(sender);
                        break;
                }
            }else{
                sender.sendMessage("§c Kauf dir die Rechte bei Adi");
            }
        }else{
            sender.sendMessage("§c Du Musst den Command als Spieler Benutzen");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<String>();

        if(args.length == 1){
            for(int i = 0; i < args0Complete.length; i++){
                tabComplete.add(args0Complete[i]);
            }
        }
        if(args.length == 2){
            tabComplete.add("deathstop");
        }
        if(args.length == 3){
            tabComplete.add("false");
            tabComplete.add("true");
        }
        return tabComplete;
    }

    private void sendUsage(CommandSender sender){
        sender.sendMessage("§9Bitte benutze /timer resume, /timer pause, /timer time <Zeit>, /timer reset, /timer settings [deathstop true/false, ]  !");
    }
    
}
