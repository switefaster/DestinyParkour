package io.github.switefaster.destinyparkour;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.switefaster.destinyparkour.arena.ArenaManager;
import io.github.switefaster.destinyparkour.command.ControlCommand;
import io.github.switefaster.destinyparkour.command.PlayerCommand;
import io.github.switefaster.destinyparkour.command.SetupCommand;
import io.github.switefaster.destinyparkour.events.ParkourEvents;
import io.github.switefaster.destinyparkour.events.SignEvents;
import io.github.switefaster.destinyparkour.handler.GlobalPlayerManager;
import io.github.switefaster.destinyparkour.internationalization.MessageHandler;

public class DestinyParkour extends JavaPlugin{
	
	private MessageHandler MessageHandler;
	private GlobalPlayerManager GPM;
	private ArenaManager AM;
	
	@Override
	public void onEnable(){
		this.getLogger().info("DestinyParkour Enabled!");
		
		if(!this.getDataFolder().exists()){
			this.getDataFolder().mkdir();
		}
		File parkourFolder = new File(this.getDataFolder(),"parkours");
		if(!parkourFolder.exists()){
			parkourFolder.mkdir();
		}
		
		this.saveResource("message.yml", false);
		
		this.GPM = new GlobalPlayerManager(this);
		this.AM = new ArenaManager(this);
		this.MessageHandler = new MessageHandler(this);
		
		this.getServer().getPluginManager().registerEvents(new ParkourEvents(this), this);
		this.getServer().getPluginManager().registerEvents(new SignEvents(this), this);
		
		this.getServer().getPluginCommand("destinyparkour").setExecutor(new PlayerCommand(this));
		this.getServer().getPluginCommand("destinyparkourcontrol").setExecutor(new ControlCommand(this));
		this.getServer().getPluginCommand("destinyparkoursetup").setExecutor(new SetupCommand(this));
	}
	
	@Override
	public void onDisable(){
		this.getLogger().info("DestinyParkour Disabled!");
	}
	
	public MessageHandler getMessageHandler(){
		return this.MessageHandler;
	}
	
	public GlobalPlayerManager getGlobalPlayerManager(){
		return this.GPM;
	}
	
	public ArenaManager getArenaManager(){
		return this.AM;
	}
	
}
