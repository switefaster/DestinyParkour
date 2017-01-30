package io.github.switefaster.destinyparkour.handler;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class GlobalPlayerManager {
	
	private DestinyParkour plugin;
	
	private HashMap<Player,ParkourArena> AllPlayers = new HashMap<Player,ParkourArena>();
	
	public GlobalPlayerManager(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	public void addPlayer(Player player,ParkourArena arena){
		this.AllPlayers.put(player, arena);
		player.sendMessage(plugin.getMessageHandler().getMessage("STATS.JOIN_ARENA").replace("${BUILDER}$", arena.getBuilder()).replace("${NAME}$", arena.getName()));
	}
	
	public HashSet<Player> getGlobalPlayers(){
		return new HashSet<Player>(this.AllPlayers.keySet());
	}
	
	public ParkourArena getPlayerArena(Player player){
		return this.AllPlayers.get(player);
	}
	
	public void removePlayer(Player player,ParkourArena arena){
		this.AllPlayers.remove(player, arena);
		player.sendMessage(plugin.getMessageHandler().getMessage("STATS.LEAVE_ARENA").replace("${NAME}$", arena.getName()));
	}
	
	public boolean isPlayer(Player player){
		return this.AllPlayers.containsKey(player);
	}
	
	public HashMap<Player,ParkourArena> getGlobalPlayersMap(){
		return this.AllPlayers;
	}

}
