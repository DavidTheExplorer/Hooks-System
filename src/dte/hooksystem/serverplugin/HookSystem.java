package dte.hooksystem.serverplugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.utils.OnlineOperators;
import dte.hooksystem.utils.PluginUtils;

public class HookSystem extends JavaPlugin
{
	private static HookSystem INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		OnlineOperators.init();
		
		PluginUtils.logToConsole(this, ChatColor.GREEN + "Listening to incoming Plugins' Hooks...");
	}
	
	public static HookSystem getInstance()
	{
		return INSTANCE;
	}
}