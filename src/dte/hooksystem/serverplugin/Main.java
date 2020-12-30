package dte.hooksystem.serverplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.listeners.HookListener;
import dte.hooksystem.utils.ChatColorUtils;

public class Main extends JavaPlugin implements HookListener
{
	private static Main INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
	}
	public static Main getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public void onHook(Plugin owningPlugin, PluginHook hook) 
	{
		String hookMessage = String.format("[HookSystem] GREEN+%s GRAY+has successfully hooked to AQUA+%sGRAY+!", owningPlugin.getName(), hook.getPluginName());
		
		logToConsole(ChatColorUtils.colorizeLiterals(hookMessage));
	}
	private void logToConsole(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
}