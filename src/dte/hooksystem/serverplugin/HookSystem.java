package dte.hooksystem.serverplugin;

import org.bukkit.ChatColor;

import dte.hooksystem.utils.ModernJavaPlugin;

public class HookSystem extends ModernJavaPlugin
{
	private static HookSystem INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		logToConsole(ChatColor.GREEN + "Listening to incoming Plugins' Hooks...");
	}
	
	public static HookSystem getInstance()
	{
		return INSTANCE;
	}
}