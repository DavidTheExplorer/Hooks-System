package com.hooksystem.exampleplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.hooksystem.exampleplugin.permissions.PermissionsManager;

public class DisplayGroupListener implements Listener
{
	private PermissionsManager permissionsManager;
	
	public DisplayGroupListener(PermissionsManager permissionsManager) 
	{
		this.permissionsManager = permissionsManager;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		String playerGroupName = this.permissionsManager.getPlayerGroup(player.getUniqueId());
		
		Bukkit.broadcastMessage(String.format("%s belongs to %s group", player.getName(), playerGroupName));
	}
}