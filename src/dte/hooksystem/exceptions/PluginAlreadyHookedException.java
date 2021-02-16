package dte.hooksystem.exceptions;

import org.bukkit.plugin.Plugin;

/**
 * Thrown when a developer attempts to register another hook for the same plugin.
 */
public class PluginAlreadyHookedException extends HookException
{
	private final Plugin plugin;
	
	private static final long serialVersionUID = -8548600593534415155L;
	
	public PluginAlreadyHookedException(Plugin plugin)
	{
		super(plugin.getName(), "Tried to register a hook for the %plugin plugin but one already exists!");
		
		this.plugin = plugin;
	}
	public Plugin getPlugin() 
	{
		return this.plugin;
	}
}