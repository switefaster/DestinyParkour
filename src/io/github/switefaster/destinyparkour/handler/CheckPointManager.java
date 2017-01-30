package io.github.switefaster.destinyparkour.handler;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.arena.ParkourArena;

public class CheckPointManager {
	
	private ParkourArena arena;
	private DestinyParkour plugin;
	
	public CheckPointManager(ParkourArena arena,DestinyParkour plugin){
		this.arena = arena;
		this.plugin = plugin;
	}
	
	public boolean isCheckPoint(Location loc){
		File config = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		ParkourData = load(config);
		for(int i = 0;i<ParkourData.getInt("SpawnPointsCount");i++){
			if(((Location)ParkourData.get("SpawnPoints."+i)).equals(loc)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isFinish(Location loc){
		File config = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		ParkourData = load(config);
		if(((Location)ParkourData.get("Config.End")).equals(loc)){
			return true;
		}
		return false;
	}
	
	public int getCheckPointIndex(Location checkpoint){
		File config = new File(plugin.getDataFolder(),"parkours/"+arena.getName()+".yml");
		FileConfiguration ParkourData;
		ParkourData = load(config);
		for(int i = 0;i<ParkourData.getInt("SpawnPointsCount");i++){
			if(((Location)ParkourData.get("SpawnPoints."+i)).equals(checkpoint)){
				return i;
			}
		}
		return -1;
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
