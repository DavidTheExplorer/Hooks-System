package dte.hooksystem.exceptions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Thrown when a developer tries to register another hook for the same plugin.
 */
public class PluginAlreadyHookedException extends HookException
{
	private final Plugin plugin;
	
	private static final long serialVersionUID = -8548600593534415155L;
	
	public PluginAlreadyHookedException(String pluginName)
	{
		super(pluginName, "Tried to register a hook for the %plugin plugin but one already exists!");
		
		this.plugin = Bukkit.getPluginManager().getPlugin(pluginName);
	}
	public Plugin getPlugin() 
	{
		return this.plugin;
	}
}