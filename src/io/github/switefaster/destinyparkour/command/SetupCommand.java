package io.github.switefaster.destinyparkour.command;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class SetupCommand implements CommandExecutor{
	
	private DestinyParkour plugin;
	
	private ParkourArena arena;
	
	private boolean usedspawn;
	private boolean usedend;
	private boolean usedlobby;
	
	private HashMap<Integer,Location> checkpoint = new HashMap<Integer,Location>();

	public SetupCommand(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
		if(cmd.getName().equalsIgnoreCase("destinyparkoursetup")){
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(args[0]!=null)
				if(args[0].equalsIgnoreCase("create")){
					if(args.length == 3){
						this.arena = new ParkourArena(args[1],args[2],plugin);
						if(!(plugin.getGlobalPlayerManager().isPlayer(player))){
							this.arena.getSetupManager().setName(args[1]);
							this.arena.getSetupManager().setBuilder(args[2]);
							sender.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_SPAWN_LOBBY_FINISH"));
							return true;
						}
					}
					else{
						sender.sendMessage("Args count not right!");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("spawn")){
					this.arena.getSetupManager().setSpawn(new Location(player.getWorld(),player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ()));
					player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_SPAWN"));
					this.usedspawn = true;
					return true;
				}
				if(args[0].equalsIgnoreCase("finish")){
					this.arena.getSetupManager().setEnd(new Location(player.getWorld(),player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ()));
					this.usedend = true;
					player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_END"));
					return true;
				}
				if(args[0].equalsIgnoreCase("lobby")){
					this.arena.getSetupManager().setLobby(new Location(player.getWorld(),player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ()));
					player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_LOBBY"));
					this.usedlobby = true;
					return true;
				}
				if(args[0].equalsIgnoreCase("checkpoint")){
					if(this.usedend==true && this.usedlobby==true && this.usedspawn==true){
						Location loc = new Location(player.getWorld(),player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
						this.checkpoint.put(this.checkpoint.size(), loc);
						player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_CHECKPOINT").replace("${NOW_SETTING_CHECKPOINT}$", String.valueOf(checkpoint.size())));
						return true;
					}
					else{
						player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SET_LOBBY_END_SPAWN_FIRST"));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("save")){
					if(this.checkpoint != null){
						this.arena.getSetupManager().setCheckPoints(checkpoint);
					}
					else{
						sender.sendMessage(plugin.getMessageHandler().getMessage("SETUP.ERROR_NO_CHECK_POINT"));
						return true;
					}
					if(this.arena.getSetupManager().save()){
						player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.SUCCESSFUL"));
						this.arena = null;
						this.usedend = false;
						this.usedlobby = false;
						this.usedspawn = false;
						this.checkpoint = null;
						return true;
					}
					else{
						player.sendMessage(plugin.getMessageHandler().getMessage("SETUP.FAILED"));
						return true;
					}
				}
				return false;
			}
			else{
				sender.sendMessage("This command can only use by a player!");
			}
		}
		
		return true;
	}
	
}
