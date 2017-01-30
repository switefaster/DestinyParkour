package io.github.switefaster.destinyparkour.handler;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class ParkourHandler {
	private ParkourArena arena;
	private DestinyParkour plugin;
	
	public ParkourHandler(DestinyParkour plugin,ParkourArena arena){
		this.plugin = plugin;
		this.arena = arena;
	}
	
	public void ResetPlayer(Player player){
		File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		if(!pdf.exists()){
			player.sendMessage(plugin.getMessageHandler().getMessage("STATS.PARKOUR_NOT_EXISTS"));
			return;
		}
		ParkourData = load(pdf);
		if(!ParkourData.contains("Player."+player.getName()+".LatestCheckPoint")){
			Location spawn = (Location)ParkourData.get("Config.Spawn");
			player.teleport(spawn);
			return;
		}
		int CPI = ParkourData.getInt("Player."+player.getName()+".LatestCheckPoint");
		Location LatestCheckPoint = (Location)ParkourData.get("SpawnPoints."+CPI);
		player.teleport(LatestCheckPoint);
		player.sendMessage(plugin.getMessageHandler().getMessage("STATS.LOAD_LATEST_CHECKPOINT"));
	}
	
	public void CheckPointPlayer(Player player,int checkpointindex){
		File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		if(!pdf.exists()){
			player.sendMessage(plugin.getMessageHandler().getMessage("STATS.PARKOUR_NOT_EXISTS"));
			return;
		}
		ParkourData = load(pdf);
		if((ParkourData.contains("Player."+player.getName()+".LatestCheckPoint"))){
			if(ParkourData.getInt("Player."+player.getName()+".LatestCheckPoint")>=(checkpointindex-1)){
				if(ParkourData.getInt("Player."+player.getName()+".LatestCheckPoint")==checkpointindex){
					return;
				}
				player.sendMessage(plugin.getMessageHandler().getMessage("STATS.REACHED_CHECKPOINT").replace("${CHECKPOINT}$", String.valueOf(checkpointindex+1)));
			}
			else{
				player.sendMessage(plugin.getMessageHandler().getMessage("STATS.YOU_CHEAT").replace("${NEXT_CHECKPOINT}$", String.valueOf((ParkourData.getInt("Player."+player.getName()+".LatestCheckPoint"))+2)));
				Location latestCheckPoint = (Location)ParkourData.get("SpawnPoints."+ParkourData.getInt("Player."+player.getName()+".LatestCheckPoint"));
				player.teleport(latestCheckPoint);
				return;
			}
		}
		else{
			player.sendMessage(plugin.getMessageHandler().getMessage("STATS.REACHED_CHECKPOINT").replace("${CHECKPOINT}$", String.valueOf(checkpointindex+1)));
		}
		ParkourData.set("Player."+player.getName()+".LatestCheckPoint",checkpointindex);
		try{
			ParkourData.save(pdf);
		}
		catch(IOException err){
			err.printStackTrace();
		}
	}
	
	public void PlayerFinish(Player player){
		File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		if(!pdf.exists()){
			player.sendMessage(plugin.getMessageHandler().getMessage("STATS.PARKOUR_NOT_EXISTS"));
			return;
		}
		ParkourData = load(pdf);
		Location lobby = (Location)ParkourData.get("Config.Lobby");
		player.teleport(lobby);
		ParkourData.set("Player."+player.getName()+".LatestCheckPoint",null);
		try{
			ParkourData.save(pdf);
		}
		catch(IOException err){
			err.printStackTrace();
		}
		player.sendMessage(plugin.getMessageHandler().getMessage("STATS.PARKOUR_FINISH").replace("${NAME}$", arena.getName()));
		plugin.getGlobalPlayerManager().getPlayerArena(player).getPlayerManager().playerQuit(player);
		plugin.getGlobalPlayerManager().removePlayer(player,plugin.getGlobalPlayerManager().getPlayerArena(player));
	}
	
	public void handleGame(){
		new BukkitRunnable(){
			@Override
			public void run(){
				File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
				FileConfiguration ParkourData;
				ParkourData = load(pdf);
				if(!pdf.exists()){
					plugin.getLogger().info(plugin.getMessageHandler().getMessage("STATS.PARKOUR_NOT_EXISTS"));
					return;
				}
				for(Player player : plugin.getGlobalPlayerManager().getGlobalPlayers()){
					ParkourArena arena = plugin.getGlobalPlayerManager().getPlayerArena(player);
					Location loc = player.getLocation().getBlock().getLocation();
					loc.setPitch(0.0f);
					loc.setYaw(0.0f);
					if(arena.getCheckPointManager().isCheckPoint(loc)){
						if(ParkourData.contains("Player."+player.getName()+".LatestCheckPoint")){
							arena.getParkourHandler().CheckPointPlayer(player, arena.getCheckPointManager().getCheckPointIndex(loc));
						}
						else{
							arena.getParkourHandler().CheckPointPlayer(player, 0);
						}
					}
					if(arena.getCheckPointManager().isFinish(loc)){
						arena.getParkourHandler().PlayerFinish(player);
					}
				}
			}
		}.runTaskTimer(plugin, 0L, 1L);
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
