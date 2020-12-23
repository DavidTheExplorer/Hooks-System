package dte.hooksystem.exampleplugin;

import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.logErrorToConsole;
import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.MessageStylesFactory.withPluginPrefix;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.exampleplugin.hooks.WorldGuardHook;
import dte.hooksystem.exampleplugin.hooks.permissionmanagers.LuckPermsHook;
import dte.hooksystem.exampleplugin.hooks.permissionmanagers.PermissionsManagerProvider;
import dte.hooksystem.exampleplugin.listeners.DisplayGroupListeners;
import dte.hooksystem.exampleplugin.listeners.DisplayRegionListeners;
import dte.hooksystem.exampleplugin.permissions.LocalPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.HooksManager;

public class ExampleMain extends JavaPlugin
{
	private HooksManager hooksManager;
	private PermissionsManager permissionsManager;

	@Override
	public void onEnable()
	{
		this.hooksManager = new HooksManager(this);

		//Register the hooks for WorldGuard and LuckPerms [Suggestion: always static import logToConsole() and withPluginPrefix()]
		this.hooksManager.startHookingTo(new WorldGuardHook(), new LuckPermsHook())
		.optional()
		.ifPluginAbsent(logErrorToConsole(withPluginPrefix(this), "%plugin wasn't found on this server!", "will not use any functionality of it."))
		.hook();

		/*
		 * |V| Plugin Workflow |V|
		 */

		//Find a Permissions Manager Hook, otherwise use the local one
		this.permissionsManager = findPermissionsManager().orElse(new LocalPermissionsManager());

		//Display the online players' groups
		Bukkit.getOnlinePlayers().stream()
		.collect(groupingBy(player -> this.permissionsManager.getPlayerGroupName(player.getUniqueId())))
		.forEach(this::displayGroup);

		//Example Listeners
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new DisplayGroupListeners(this.permissionsManager), this);
		hooksManager.findHook(WorldGuardHook.class).ifPresent(worldGuardHook -> pm.registerEvents(new DisplayRegionListeners(worldGuardHook), this));
		System.out.println("Hello! x2");
	}
	public PermissionsManager getPermissionsManager()
	{	
		return this.permissionsManager;
	}
	private Optional<PermissionsManager> findPermissionsManager()
	{
		//Example of the extensibility of the Library: Using an interface for Permissions Managers plugins such as PermissionsEX and GroupManager
		return this.hooksManager
				.findHookOf(PermissionsManagerProvider.class, () -> 
				{
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CONFLICT: More than one Permissions Manager detected! Closing the plugin...");
					Bukkit.getPluginManager().disablePlugin(this);
				})
				.map(PermissionsManagerProvider::getPermissionsManager);
	}
	private void displayGroup(String groupName, List<? extends Player> players) 
	{
		String playersNames = players.stream()
				.map(Player::getName)
				.collect(joining(", "));

		System.out.println(String.format("%s(%d players): %s", groupName, players.size(), playersNames));
	}
}
