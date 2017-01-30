package io.github.switefaster.destinyparkour.events;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.switefaster.destinyparkour.DestinyParkour;

public class SignEvents implements Listener{
	
	private DestinyParkour plugin;
	
	public SignEvents(DestinyParkour plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void createSignEvent(SignChangeEvent evt){
		Player player = evt.getPlayer();
		if(evt.getLine(0).equalsIgnoreCase("[Parkour]")){
			if(!player.hasPermission("destinyparkour.setup")){
				player.sendMessage(plugin.getMessageHandler().getMessage("STATS.NO_SETUP_PERMISSION"));
				evt.setCancelled(true);
				evt.getBlock().breakNaturally();
				return;
			}
			else{
				if(plugin.getArenaManager().containsArenaName(evt.getLine(1))){
					evt.setLine(3, ChatColor.translateAlternateColorCodes('&',"&5"+plugin.getArenaManager().getParkourArena(evt.getLine(1)).getBuilder()));
					evt.setLine(2, evt.getLine(1));
					evt.setLine(0, ChatColor.translateAlternateColorCodes('&',"&1[Parkour]"));
					evt.setLine(1, ChatColor.translateAlternateColorCodes('&',"&aJoin"));
					return;
				}
				evt.getPlayer().sendMessage(plugin.getMessageHandler().getMessage("STATS.ARENA_NOT_EXISTS"));
				evt.setCancelled(true);
				evt.getBlock().breakNaturally();
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSignClick(PlayerInteractEvent evt){
		if(evt.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		if(!(evt.getClickedBlock().getState() instanceof Sign)){
			return;
		}
		Player player = evt.getPlayer();
		Sign sign = (Sign)evt.getClickedBlock().getState();
		player.sendMessage("REACHED HERE");
		if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE+"[Parkour]")){
			if(!(player.hasPermission("destinyparkour.play"))){
				player.sendMessage(plugin.getMessageHandler().getMessage("STATS.NO_PLAY_PERMISSION"));
				evt.setCancelled(true);
				player.sendMessage("REACHED HERE");
				return;
			}
			if(plugin.getArenaManager().containsArenaName(sign.getLine(2))){
				plugin.getGlobalPlayerManager().addPlayer(player, plugin.getArenaManager().getParkourArena(sign.getLine(2)));
				plugin.getArenaManager().getParkourArena(sign.getLine(2)).getPlayerManager().playerJoin(evt.getPlayer());
				plugin.getGlobalPlayerManager().getPlayerArena(evt.getPlayer()).getParkourHandler().ResetPlayer(evt.getPlayer());
				player.sendMessage("REACHED HERE");
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void onSignDestroyed(BlockBreakEvent evt){
		if(!(evt.getBlock().getState() instanceof Sign)){
			return;
		}
		Player player = evt.getPlayer();
		Sign sign = (Sign)evt.getBlock().getState();
		if(sign.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&1[Parkour]"))){
			if(!player.hasPermission("destinyparkour.setup")){
				player.sendMessage(plugin.getMessageHandler().getMessage("STATS.NO_SETUP_PERMISSION"));
				evt.setCancelled(true);
				return;
			}
		}
	}
	
}
