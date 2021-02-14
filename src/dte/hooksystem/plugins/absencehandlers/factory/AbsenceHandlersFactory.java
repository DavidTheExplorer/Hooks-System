package dte.hooksystem.plugins.absencehandlers.factory;

import static dte.hooksystem.messagestyle.MessageStyle.RAW;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.messagestyle.MessageStyle;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;
import dte.hooksystem.plugins.absencehandlers.composite.CompositeHandler;
import dte.hooksystem.plugins.absencehandlers.composite.CompositeHandlerOptions;
import dte.hooksystem.plugins.absencehandlers.list.ActionHandler;
import dte.hooksystem.plugins.absencehandlers.list.DisablePluginHandler;
import dte.hooksystem.plugins.absencehandlers.list.DoNothingHandler;
import dte.hooksystem.plugins.absencehandlers.list.logging.LogToConsoleHandler;
import dte.hooksystem.plugins.absencehandlers.list.logging.LoggerMessageHandler;
import dte.hooksystem.plugins.absencehandlers.list.logging.MessagerHandler;

public class AbsenceHandlersFactory
{
	//Container of static factory methods
	private AbsenceHandlersFactory(){}
	
	//Cached Stateless Handlers
	public static final PluginAbsenceHandler DO_NOTHING = new DoNothingHandler();
	
	/*
	 * Console
	 */
	public static PluginAbsenceHandler logToConsole(String... messages)
	{
		return logToConsole(RAW, messages);
	}
	public static PluginAbsenceHandler logToConsole(MessageStyle style, String... messages) 
	{
		LogToConsoleHandler handler = new LogToConsoleHandler();
		addStyledMessages(handler, style, messages);

		return handler;
	}
	public static PluginAbsenceHandler logErrorToConsole(String... messages) 
	{
		//"Error" means that the messages become red
		MessageStyle redStyle = new MessageStyle()
				.withFinalTouch(message -> ChatColor.RED + message);

		return logToConsole(redStyle, messages);
	}
	public static PluginAbsenceHandler logErrorToConsole(MessageStyle style, String... messages) 
	{
		//"Error" means that the messages become red
		MessageStyle redStyle = style.withFinalTouch(message -> ChatColor.RED + message);

		return logToConsole(redStyle, messages);
	}

	/*
	 * java.util.logging.Logger
	 */
	public static PluginAbsenceHandler log(Logger logger, Level logLevel, MessageStyle style, String... messages) 
	{
		LoggerMessageHandler handler = new LoggerMessageHandler(logger, logLevel);
		addStyledMessages(handler, style, messages);
		
		return handler;
	}
	public static PluginAbsenceHandler log(Logger logger, Level logLevel, String... messages) 
	{
		return log(logger, logLevel, RAW, messages);
	}


	/*
	 * General
	 */
	public static PluginAbsenceHandler run(Consumer<PluginHook> action)
	{
		return new ActionHandler(action);
	}
	public static PluginAbsenceHandler disablePlugin(Plugin plugin) 
	{
		return new DisablePluginHandler(plugin);
	}
	public static PluginAbsenceHandler handleInOrder(PluginAbsenceHandler... handlers)
	{
		return CompositeHandler.of(CompositeHandlerOptions.FIFO, handlers);
	}
	
	private static void addStyledMessages(MessagerHandler handler, MessageStyle style, String... messages) 
	{
		String[] styledMessages = style.apply(Arrays.asList(messages));
		
		handler.addMessages(styledMessages);
	}
	
	public static class MessageStylesFactory 
	{
		public static MessageStyle withPluginPrefix(Plugin plugin) 
		{
			return new MessageStyle()
					.prefixedWith(String.format("[%s] ", plugin.getName()));
		}
	}
}