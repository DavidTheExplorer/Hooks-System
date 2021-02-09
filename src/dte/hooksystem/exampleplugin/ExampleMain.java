package dte.hooksystem.exampleplugin;

import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.logErrorToConsole;
import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.MessageStylesFactory.withPluginPrefix;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.api.HookSystemAPI;
import dte.hooksystem.exampleplugin.hooks.WorldGuardHook;
import dte.hooksystem.exampleplugin.hooks.permissionmanagers.LuckPermsHook;
import dte.hooksystem.exampleplugin.hooks.permissionmanagers.PermissionsManagerHook;
import dte.hooksystem.exampleplugin.listeners.DisplayGroupListeners;
import dte.hooksystem.exampleplugin.listeners.DisplayRegionListeners;
import dte.hooksystem.exampleplugin.permissions.LocalPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.service.IHookService;

public class ExampleMain extends JavaPlugin
{
	private IHookService hookService;
	private PermissionsManager permissionsManager;

	@Override
	public void onEnable()
	{
		this.hookService = HookSystemAPI.createHookService(this);
		
		//Register the hooks of WorldGuard and LuckPerms (Suggestion: always static import AbsenceHandlersFactory)
		HookSystemAPI.safeHookingSession(this.hookService)
		.softdepend()
		.ifPluginAbsent(logErrorToConsole(withPluginPrefix(this), "%plugin wasn't found on this server!", "will not use any functionality of it."))
		.hookTo(new WorldGuardHook(), new LuckPermsHook());
		
		this.permissionsManager = findPermissionsManager();

		//Registering Listeners
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new DisplayGroupListeners(this.permissionsManager), this);
		this.hookService.findHook(WorldGuardHook.class).ifPresent(wgHook -> pm.registerEvents(new DisplayRegionListeners(wgHook), this));

		//Displaying the online players' groups
		Bukkit.getOnlinePlayers().stream()
		.collect(groupingBy(player -> this.permissionsManager.getPlayerGroupName(player.getUniqueId())))
		.forEach(this::displayGroup);
	}
	private PermissionsManager findPermissionsManager()
	{
		return this.hookService
				.findHookOf(PermissionsManagerHook.class, managers -> 
				{
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CONFLICT: More than one Permissions Manager was detected! Closing the plugin...");
					Bukkit.getPluginManager().disablePlugin(this);
				})
				.map(PermissionsManagerHook::getPermissionsManager)
				.orElse(new LocalPermissionsManager()); //if no permissions managers were found, the plugin uses the local one

	}
	private void displayGroup(String groupName, List<? extends Player> players) 
	{
		String playersNames = players.stream()
				.map(Player::getName)
				.collect(joining(", "));

		Bukkit.broadcastMessage(String.format("%s(%d players): %s", groupName, players.size(), playersNames));
	}
}
