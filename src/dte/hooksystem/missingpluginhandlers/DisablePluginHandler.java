package dte.hooksystem.missingpluginhandlers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;

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