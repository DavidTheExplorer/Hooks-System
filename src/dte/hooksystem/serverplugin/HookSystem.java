package dte.hooksystem.serverplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.utils.OnlineOperators;

public class HookSystem extends JavaPlugin
{
	private static HookSystem INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		OnlineOperators.init();
		
		logToConsole(ChatColor.GREEN + "Listening to incoming Plugins' Hooks...");
	}
	
	public static HookSystem getInstance()
	{
		return INSTANCE;
	}
	
	private void logToConsole(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s", getDescription().getName(), message));
	}
}