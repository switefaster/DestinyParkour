package io.github.switefaster.destinyparkour.handler;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

public class PlayerManager {
	
	private HashMap<String,Player> Players = new HashMap<String,Player>();
	
	public void playerJoin(Player player){
		this.Players.put(player.getName(), player);
	}
	
	public HashSet<Player> getPlayers(){
		return new HashSet<Player>(this.Players.values());
	}
	
	public HashMap<String,Player> getPlayersMap(){
		return this.Players;
	}
	
	public void playerQuit(Player player){
		this.Players.remove(player.getName());
	}
	
	public boolean isPlayer(Player player){
		return this.Players.containsKey(player.getName());
	}
	
}
