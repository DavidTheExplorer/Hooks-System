package dte.hooksystem.plugins.absencehandlers.list.logging;

import org.bukkit.Bukkit;

public class LogToConsoleHandler extends MessagerHandler
{
	@Override
	public void sendMessage(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
}