package dte.hooksystem.plugins.absencehandlers.list.logging;

import org.bukkit.Bukkit;

public class LogToConsoleHandler extends MessagerHandler
{
	public LogToConsoleHandler(String[] messages)
	{
		super(messages);
	}
	
	@Override
	public void sendMessage(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	@Override
	public MessagerHandler copy() 
	{
		return new LogToConsoleHandler(getCopiedMessages());
	}
}