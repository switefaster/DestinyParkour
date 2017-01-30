package io.github.switefaster.destinyparkour.events;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class ParkourEvents implements Listener{
	
	private DestinyParkour plugin;
	
	public ParkourEvents(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void playerRespawnEvent(EntityDeathEvent evt){
		if(evt.getEntity() instanceof Player){
			if(plugin.getGlobalPlayerManager().isPlayer((Player)evt.getEntity())){
				plugin.getGlobalPlayerManager().getPlayerArena((Player)evt.getEntity()).getPlayerManager().playerQuit((Player)evt.getEntity());
				plugin.getGlobalPlayerManager().removePlayer((Player)evt.getEntity(),plugin.getGlobalPlayerManager().getPlayerArena((Player)evt.getEntity()));
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void playerQuitEvent(PlayerQuitEvent evt){
		if(plugin.getGlobalPlayerManager().isPlayer(evt.getPlayer())){
			ParkourArena arena = plugin.getGlobalPlayerManager().getPlayerArena(evt.getPlayer());
			File pdf = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
			FileConfiguration ParkourData = load(pdf);
			
			Location lobby = (Location)ParkourData.get("Config.Lobby");
			evt.getPlayer().teleport(lobby);
			
			plugin.getGlobalPlayerManager().getPlayerArena(evt.getPlayer()).getPlayerManager().playerQuit(evt.getPlayer());
			plugin.getGlobalPlayerManager().removePlayer(evt.getPlayer(),plugin.getGlobalPlayerManager().getPlayerArena(evt.getPlayer()));
		}
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
