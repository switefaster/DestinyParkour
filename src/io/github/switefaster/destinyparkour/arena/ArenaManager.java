package io.github.switefaster.destinyparkour.arena;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.switefaster.destinyparkour.DestinyParkour;

public class ArenaManager {
	
	private HashMap<String,ParkourArena> ArenaList = new HashMap<String,ParkourArena>();
	
	private DestinyParkour plugin;
	
	public ArenaManager(DestinyParkour plugin){
		this.plugin = plugin;
		loadArenas();
	}
	
	public void loadArenas(){
		File[] arenas = getAllArenas();
		int arenasCount = arenas.length;
		for(int index=0;index<arenasCount;index++){
			FileConfiguration arena = load(arenas[index]);
			this.ArenaList.put(arena.getString("Name"), new ParkourArena(arena.getString("Name"),arena.getString("Builder"),this.plugin));
		}
	}
	
	public void addArena(String name,ParkourArena arena){
		this.ArenaList.put(name, arena);
	}
	
	public ParkourArena getParkourArena(String name){
		return this.ArenaList.get(name);
	}
	
	public boolean containsArena(ParkourArena arena){
		return this.ArenaList.containsValue(arena);
	}
	
	public boolean containsArenaName(String name){
		return this.ArenaList.containsKey(name);
	}
	
	public void disableArena(ParkourArena arena){
		this.ArenaList.remove(arena.getName(), arena);
	}
	
	public boolean isArenaDisabledContains(String name){
		File cfg = new File(plugin.getDataFolder(),"parkours/"+name+".yml");
		if(cfg.exists()){
			return true;
		}
		return false;
	}
	
	public FileConfiguration getArenaConfig(String name){
		File cfg = new File(plugin.getDataFolder(),"parkours/"+name+".yml");
		return load(cfg);
	}
	
	public HashMap<String,ParkourArena> getArenaList(){
		return this.ArenaList;
	}
	
	private File[] getAllArenas(){
		File arenasFolder = new File(plugin.getDataFolder(),"parkours");
		if(!arenasFolder.exists()){
			plugin.getLogger().info("Arenas folder does not exist or no arenas!");
		}
		
		return arenasFolder.listFiles();
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
