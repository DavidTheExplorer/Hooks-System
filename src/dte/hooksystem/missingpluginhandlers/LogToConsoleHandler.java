package dte.hooksystem.missingpluginhandlers;

import org.bukkit.Bukkit;

import dte.hooksystem.missingpluginhandlers.messager.MessagerHandler;

public class LogToConsoleHandler extends MessagerHandler
{
	@Override
	public void sendMessage(String message) 
	{
		Bukkit.getConsoleSender().sendMessage(message);
	}
}