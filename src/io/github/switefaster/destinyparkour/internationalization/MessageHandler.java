package io.github.switefaster.destinyparkour.internationalization;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.github.switefaster.destinyparkour.DestinyParkour;

public class MessageHandler {
	private FileConfiguration lang;
	
	public MessageHandler(DestinyParkour plugin){
		try{
			File langfile = new File(plugin.getDataFolder(),"message.yml");
			lang = load(langfile);
		}
		catch(Exception err){
			err.printStackTrace();
		}
	}
	
	public String getMessage(String key){
		if(lang.contains(key)){
			String preFix = ChatColor.GREEN+"[DestinyParkour]";
			String returnString = preFix+ChatColor.translateAlternateColorCodes('&', lang.getString(key));
			return returnString;
		}
		else{
			return "Message not found:"+key;
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