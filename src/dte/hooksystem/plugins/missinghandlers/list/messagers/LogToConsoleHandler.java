package dte.hooksystem.plugins.missinghandlers.list.messagers;

import org.bukkit.Bukkit;

public class LogToConsoleHandler extends MessagerHandler
{
	@Override
	public void sendMessage(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
}