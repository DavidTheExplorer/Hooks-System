package dte.hooksystem.serverplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class HookSystem extends JavaPlugin
{
	private static HookSystem INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
	}
	
	public static HookSystem getInstance()
	{
		return INSTANCE;
	}
}