package io.github.switefaster.destinyparkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class ControlCommand implements CommandExecutor{
	
	private DestinyParkour plugin;
	
	public ControlCommand(DestinyParkour plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args[0]!=null)
		if(cmd.getName().equalsIgnoreCase("destinyparkourcontrol")){
			if(args[0].equalsIgnoreCase("enable")){
				if(!(plugin.getArenaManager().containsArenaName(args[1]))){
					if(plugin.getArenaManager().isArenaDisabledContains(args[1])){
						plugin.getArenaManager().addArena(args[1], new ParkourArena(plugin.getArenaManager().getArenaConfig(args[1]).getString("Name"),plugin.getArenaManager().getArenaConfig(args[1]).getString("Builder"),plugin));
						return true;
					}
					else{
						sender.sendMessage(plugin.getMessageHandler().getMessage("STATS.ARENA_ALREADY_DELETED"));
						return true;
					}
				}
				else{
					sender.sendMessage(plugin.getMessageHandler().getMessage("STATS.ARENA_ALREADY_ENABLED"));
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("disable")){
				if(plugin.getArenaManager().containsArenaName(args[1])){
					plugin.getArenaManager().disableArena(plugin.getArenaManager().getParkourArena(args[1]));
					return true;
				}
				else{
					sender.sendMessage(plugin.getMessageHandler().getMessage("STATS.ARENA_ALREADY_DISABLED"));
					return true;
				}
			}
			return false;
		}
		return true;
	}

}
