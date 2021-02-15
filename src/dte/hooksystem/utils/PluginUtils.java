package dte.hooksystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtils 
{
	//Container of static methods
	private PluginUtils(){}
	
	public static void logToConsole(Plugin plugin, String message) 
	{
		String pluginName = plugin.getDescription().getName();
		
		Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s", pluginName, message));
	}
}
