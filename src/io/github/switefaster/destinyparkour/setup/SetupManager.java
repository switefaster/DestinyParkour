package io.github.switefaster.destinyparkour.setup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.switefaster.destinyparkour.DestinyParkour;

public class SetupManager {
	
	private DestinyParkour plugin;
	
	private String name;
	private String builder;
	private Location lobby;
	private Location spawn;
	private Location end;
	private HashMap<Integer,Location> checkpoints = new HashMap<Integer,Location>();
	
	public SetupManager(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setBuilder(String builder){
		this.builder = builder;
	}
	
	public void setLobby(Location lobby){
		this.lobby = lobby;
	}
	
	public void setSpawn(Location spawn){
		this.spawn = spawn;
	}
	
	public void setEnd(Location end){
		this.end = end;
	}
	
	public void setCheckPoints(HashMap<Integer,Location> checkpoints){
		this.checkpoints = checkpoints;
	}
	
	public int getCheckPointsCount(){
		return this.checkpoints.size();
	}
	
	private boolean checkArgs(String name,String builder,Location lobby,Location spawn,Location end,HashMap<Integer,Location> checkpoints){
		if(name!=null && builder!=null && lobby!=null && spawn!=null && end!=null && checkpoints!=null){
			return true;
		}
		return false;
	}
	
	public boolean save(){
		if(checkArgs(this.name,this.builder,this.lobby,this.spawn,this.end,this.checkpoints)){
			File cfg = new File(plugin.getDataFolder(),"parkours/"+name+".yml");
			FileConfiguration arena = load(cfg);
			arena.set("Name", name);
			arena.set("Builder", builder);
			arena.set("Config.Spawn", spawn);
			arena.set("Config.Lobby", lobby);
			arena.set("Config.End", end);
			arena.set("SpawnPointsCount", checkpoints.size());
			for(int index = 0;index < checkpoints.size();index++){
				arena.set("SpawnPoints."+index, this.checkpoints.get(index));
			}
			try{
				arena.save(cfg);
			}
			catch(IOException err){
				err.printStackTrace();
			}
			return true;
		}
		else{
			return false;
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
