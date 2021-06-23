package dte.hooksystem.serverplugin;

import org.bukkit.ChatColor;

import dte.hooksystem.utils.ModernJavaPlugin;
import dte.hooksystem.utils.OnlineOperators;

public class HookSystem extends ModernJavaPlugin
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
}