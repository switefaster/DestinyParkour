package io.github.switefaster.destinyparkour.command;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class PlayerCommand implements CommandExecutor{
	
	private DestinyParkour plugin;
	
	public PlayerCommand(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
		if(cmd.getName().equalsIgnoreCase("destinyparkour")){
			if(sender instanceof Player){
				if(args[0]!=null)
				if(args[0].equalsIgnoreCase("join")){
					if(plugin.getArenaManager().containsArenaName(args[1])){
						plugin.getGlobalPlayerManager().addPlayer((Player)sender, plugin.getArenaManager().getParkourArena(args[1]));
						plugin.getArenaManager().getParkourArena(args[1]).getPlayerManager().playerJoin((Player)sender);
						plugin.getGlobalPlayerManager().getPlayerArena((Player)sender).getParkourHandler().ResetPlayer((Player)sender);
						return true;
					}
					sender.sendMessage(plugin.getMessageHandler().getMessage("STATS.ARENA_NOT_EXISTS"));
					return false;
				}
				else if(args[0].equalsIgnoreCase("leave")){
					if(plugin.getGlobalPlayerManager().isPlayer((Player)sender)){
						ParkourArena arena = plugin.getGlobalPlayerManager().getPlayerArena((Player)sender);
						File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
						FileConfiguration ParkourData = load(pdf);
						
						Location lobby = (Location)ParkourData.get("Config.Lobby");
						((Player)sender).teleport(lobby);
						plugin.getGlobalPlayerManager().getPlayerArena((Player)sender).getPlayerManager().playerQuit((Player)sender);
						plugin.getGlobalPlayerManager().removePlayer((Player)sender,plugin.getGlobalPlayerManager().getPlayerArena((Player)sender));
						return true;
					}
					else{
						sender.sendMessage(plugin.getMessageHandler().getMessage("STATS.NOT_IN_A_PARKOUR"));
						return true;
					}
				}
				return false;
			}
			else{
				sender.sendMessage("This command can only use by a player!");
				return true;
			}
		}
		return true;
	}
	
	private FileConfiguration load(File file){
		if(!file.exists()){
			try{
				file.createNewFile();
			}
			catch(Exception err){
				err.printStackTrace();
			}
		}
		return YamlConfiguration.loadConfiguration(file);
	}
	
}
