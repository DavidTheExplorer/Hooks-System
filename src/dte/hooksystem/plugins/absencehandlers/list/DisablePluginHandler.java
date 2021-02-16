package dte.hooksystem.plugins.absencehandlers.list;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public class DisablePluginHandler implements PluginAbsenceHandler
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