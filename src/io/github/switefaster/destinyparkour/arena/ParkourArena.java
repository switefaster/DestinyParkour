package io.github.switefaster.destinyparkour.arena;

import io.github.switefaster.destinyparkour.DestinyParkour;
import io.github.switefaster.destinyparkour.handler.CheckPointManager;
import io.github.switefaster.destinyparkour.handler.ParkourHandler;
import io.github.switefaster.destinyparkour.handler.PlayerManager;
import io.github.switefaster.destinyparkour.setup.SetupManager;

public class ParkourArena {
	
	private PlayerManager PPM = new PlayerManager();
	private ParkourHandler PPH;
	private CheckPointManager CPM;
	private SetupManager SM;
	
	private String Name;
	private String Builder;
	
	public ParkourArena(String name,String builder,DestinyParkour plugin){
		this.Name = name;
		this.Builder = builder;
		this.PPH = new ParkourHandler(plugin,this);
		this.CPM = new CheckPointManager(this,plugin);
		this.SM = new SetupManager(plugin);
		this.PPH.handleGame();
	}
	
	public String getName(){
		return this.Name;
	}
	
	public PlayerManager getPlayerManager(){
		return this.PPM;
	}
	
	public ParkourHandler getParkourHandler(){
		return this.PPH;
	}
	
	public CheckPointManager getCheckPointManager(){
		return this.CPM;
	}
	
	public SetupManager getSetupManager(){
		return this.SM;
	}
	
	public String getBuilder(){
		return this.Builder;
	}
	
	public int getPlayerCount(){
		return this.getPlayerManager().getPlayers().size();
	}
	
}