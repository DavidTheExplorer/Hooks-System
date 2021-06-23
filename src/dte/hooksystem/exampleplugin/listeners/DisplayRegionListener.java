package dte.hooksystem.exampleplugin.listeners;

import static org.bukkit.ChatColor.YELLOW;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import dte.hooksystem.exampleplugin.hooks.WorldGuardHook;

public class DisplayRegionListener implements Listener
{
	private final WorldGuardHook worldGuardHook;

	public DisplayRegionListener(WorldGuardHook worldGuardHook) 
	{
		this.worldGuardHook = worldGuardHook;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		ProtectedRegion playerRegion = this.worldGuardHook.getPlayerRegion(player);
		
		if(playerRegion != null)
			Bukkit.broadcastMessage(String.format(YELLOW + "%s has left the server at %s", player.getName(), playerRegion.getId()));
		else
			Bukkit.broadcastMessage(String.format(YELLOW + "%s has left the server, and they weren't in a region.", player.getName()));
	}
}