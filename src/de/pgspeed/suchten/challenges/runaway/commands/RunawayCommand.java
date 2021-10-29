package de.pgspeed.suchten.challenges.runaway.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.pgspeed.suchten.main.Main;
import de.pgspeed.suchten.challenges.runaway.Runaway;
import de.pgspeed.suchten.challenges.runaway.RunawayDirection;

public class RunawayCommand  implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Runaway runaway = Main.getInstance().getRunaway();
        
        if (sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission("runaway.command")){
                if(args.length != 0){
                    switch(args[0].toLowerCase()){
                        default:
                            SendUsage(sender);
                            break;
                        case "start":
                            if(args.length != 2){
                                SendUsage(sender);
                                break;
                            }
                            //Logik für start

                            switch(args[1].toUpperCase()){
                                default:{
                                    SendUsage(sender);
                                    break;
                                }
                                case "X" :{
                                    runaway.Start(player, RunawayDirection.X);
                                    break;
                                }
                                case "Y" :{
                                    runaway.Start(player, RunawayDirection.Y);
                                    break;
                                }
                                case "Z" :{
                                    runaway.Start(player, RunawayDirection.Z);
                                    break;
                                }
                            }

                            break;
                        case "stop":{
                            if(args.length != 1){
                                SendUsage(sender);
                                break;
                            }
                            runaway.Stop(player);
                        }
                            break;
                    }
                }else{
                    SendUsage(sender);
                }

            }else{
                sender.sendMessage("Kauf dir Rechte bei Addi");
            }

        }else{
            sender.sendMessage("Du bist kein Spieler, Du musst dir auch Minecraft kaufen;)");
        }
        return false;
    }
    private void SendUsage(CommandSender sender){
        sender.sendMessage("§4Bitte benutze /runaway start <X/Y/Z>, /runaway stop");
    }
    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
        List<String> tabCompletList = new ArrayList<String>();

        switch(args.length){
            default:
                break;
            case 1:
                tabCompletList.add("start");
                tabCompletList.add("stop");
                break;
            case 2:
                tabCompletList.add("X");
                tabCompletList.add("Y");
                tabCompletList.add("Z");
                break;
        }
        return tabCompletList;
    }

    
}
