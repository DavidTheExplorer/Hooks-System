package dte.hooksystem.serverplugin;

import static dte.hooksystem.utils.ChatColorUtils.colorizeLiterals;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.hooks.HookListener;
import dte.hooksystem.hooks.PluginHook;

public class Main extends JavaPlugin implements HookListener
{
	private static Main INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
	}
	
	@Override
	public void onHook(Plugin owningPlugin, PluginHook hook) 
	{
		logToConsole(colorizeLiterals(String.format("[HookSystem] GREEN+%s GRAY+has successfully hooked to AQUA+%sGRAY+!", owningPlugin.getName(), hook.getPluginName())));
	}
	
	public static Main getInstance()
	{
		return INSTANCE;
	}
	
	private void logToConsole(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
}