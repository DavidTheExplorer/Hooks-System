package dte.hooksystem.plugins.missinghandlers.list;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;

public class DisablePluginHandler implements MissingPluginHandler
{
	private final Plugin plugin;
	
	public DisablePluginHandler(Plugin plugin) 
	{
		this.plugin = plugin;
	}
	
	@Override
	public void handle(PluginHook failedHook)
	{
		Bukkit.getPluginManager().disablePlugin(this.plugin);
	}
}