package dte.hooksystem.exampleplugin;

import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.disablePlugin;
import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.handleInOrder;
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

import dte.hooksystem.api.HookSystemAPI;
import dte.hooksystem.exampleplugin.hooks.LuckPermsHook;
import dte.hooksystem.exampleplugin.hooks.PermissionsManagerHook;
import dte.hooksystem.exampleplugin.hooks.WorldGuardHook;
import dte.hooksystem.exampleplugin.listeners.DisplayGroupListener;
import dte.hooksystem.exampleplugin.listeners.DisplayRegionListener;
import dte.hooksystem.exampleplugin.permissions.MemoryPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.service.IHookService;

public class ExamplePlugin extends JavaPlugin
{
	private PermissionsManager permissionsManager; //The plugin supports multiple Permissions Managers by encapsulation behind an interface

	@Override
	public void onEnable()
	{
		//Each plugin that uses this library has its own HookService
		IHookService hookService = HookSystemAPI.createHookService(this);

		//Registers the hooks of WorldGuard and LuckPerms (Suggestion: always static import AbsenceHandlersFactory)
		hookService.register(new WorldGuardHook(), logErrorToConsole(withPluginPrefix(this), "WorldGuard wasn't found on this server! It won't be used."));
		hookService.register(new LuckPermsHook(), handleInOrder(logErrorToConsole(withPluginPrefix(this), "heavily depends on LuckPerms! Closing..."), disablePlugin(this)));

		//Finds a Permissions Manager Hook, Otherwise uses the default one
		this.permissionsManager = findPermissionsManager(hookService).orElse(new MemoryPermissionsManager());

		//Registers listeners
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new DisplayGroupListener(this.permissionsManager), this);
		hookService.findHook(WorldGuardHook.class).ifPresent(wgHook -> pm.registerEvents(new DisplayRegionListener(wgHook), this));

		//Displays the online players' groups
		Bukkit.getOnlinePlayers()
		.stream()
		.collect(groupingBy(player -> this.permissionsManager.getPlayerGroupName(player.getUniqueId())))
		.forEach(this::displayGroup);
	}
	private Optional<PermissionsManager> findPermissionsManager(IHookService hookService)
	{
		return hookService
				.findHookOf(PermissionsManagerHook.class, managers -> 
				{
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CONFLICT: More than one Permissions Manager was detected! Closing the plugin...");
					Bukkit.getPluginManager().disablePlugin(this);
				})
				.map(PermissionsManagerHook::getPermissionsManager);

	}
	private void displayGroup(String groupName, List<? extends Player> onlineMembers) 
	{
		String playersNames = onlineMembers.stream()
				.map(Player::getName)
				.collect(joining(", "));

		Bukkit.broadcastMessage(String.format("%s(%d online members): %s", groupName, onlineMembers.size(), playersNames));
	}
}