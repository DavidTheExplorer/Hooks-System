package com.hooksystem.exampleplugin;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.hooksystem.base.HooksManager;
import com.hooksystem.exampleplugin.hooks.WorldGuardHook;
import com.hooksystem.exampleplugin.hooks.permissionsmanagers.LuckPermsHook;
import com.hooksystem.exampleplugin.listeners.DisplayGroupListener;
import com.hooksystem.exampleplugin.listeners.DisplayRegionListener;
import com.hooksystem.exampleplugin.permissions.LocalPermissionsManager;
import com.hooksystem.exampleplugin.permissions.PermissionsManager;

public class ExampleMain extends JavaPlugin
{
	private HooksManager hooksManager;

	private PermissionsManager permissionsManager;

	@Override
	public void onEnable()
	{
		this.hooksManager = HooksManager.getInstance();

		//Simple Hook Registration
		this.hooksManager.registerHook(new LuckPermsHook());

		//Hook Registration with a Failure Handler(which runs if the plugin isn't on the server / the hook marked itself as unuseable)
		this.hooksManager.registerHook(new WorldGuardHook(), () -> Bukkit.getLogger().severe("OMG HooksSystem heavily depends on WorldGuard!!! Things that depend on it won't work!"));
		
		
		/*
		 * Usage Examples
		 */
		
		
		//1: Find a plugin that manages the permissions, or use the local one! 
		//   (Only LuckPerms is registered as a PermissionsManager Hook - so it will be used if it exists in the server.)
		this.permissionsManager = findPermissionsManager().orElse(new LocalPermissionsManager());
		System.out.println("Current Permissions Manager: " + this.permissionsManager.getName());
		System.out.println("Does Group \"lol\" exists? " + this.permissionsManager.groupExists("lol"));

		//2: Display the online players' groups
		Map<String, List<Player>> playersGroups = Bukkit.getOnlinePlayers().stream()
				.collect(groupingBy(player -> this.permissionsManager.getPlayerGroup(player.getUniqueId())));

		playersGroups.forEach((groupName, players) ->
		{
			String playersNames = players.stream().map(Player::getName).collect(joining(", "));

			System.out.println(String.format("%s(%d players): %s", groupName, players.size(), playersNames));
		});

		//3: Simple Event Listeners
		WorldGuardHook worldGuardHook = this.hooksManager.findHook("WorldGuard");
		
		if(worldGuardHook == null) 
		{
			return;
		}
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new DisplayGroupListener(this.permissionsManager), this);
		pm.registerEvents(new DisplayRegionListener(worldGuardHook), this);
	}
	public PermissionsManager getPermissionsManager()
	{	
		return this.permissionsManager;
	}
	private Optional<PermissionsManager> findPermissionsManager()
	{
		//Example of the Extensibility of the plugin, using an interface for Permissions Managers plugins such as PermissionsEX and GroupManager 
		List<PermissionsManager> permissionsManagers = this.hooksManager.findHooksOf(PermissionsManager.class);
		
		if(permissionsManagers.size() > 1) 
		{
			Bukkit.getLogger().severe("Conflict: More than one Permissions Manager detected! Closing the plugin...");
			Bukkit.getPluginManager().disablePlugin(this);
			return Optional.empty();
		}
		PermissionsManager manager = permissionsManagers.isEmpty() ? null : permissionsManagers.get(0); //to avoid IndexOutOfBoundsException
		return Optional.ofNullable(manager);
	}
}
