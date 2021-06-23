package dte.hooksystem.plugins.missinghandlers.factory;

import static dte.hooksystem.messagestyle.MessageStyle.RAW;
import static org.bukkit.ChatColor.RED;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.messagestyle.MessageStyle;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;
import dte.hooksystem.plugins.missinghandlers.composite.CompositeHandler;
import dte.hooksystem.plugins.missinghandlers.composite.CompositeHandlerOptions;
import dte.hooksystem.plugins.missinghandlers.list.ActionHandler;
import dte.hooksystem.plugins.missinghandlers.list.DisablePluginHandler;
import dte.hooksystem.plugins.missinghandlers.list.DoNothingHandler;
import dte.hooksystem.plugins.missinghandlers.list.messagers.LogToConsoleHandler;
import dte.hooksystem.plugins.missinghandlers.list.messagers.LoggerMessageHandler;
import dte.hooksystem.plugins.missinghandlers.list.messagers.MessagerHandler;

public class MissingHandlersFactory
{
	//Container of static factory methods
	private MissingHandlersFactory(){}
	
	//Cached Stateless Handlers
	public static final MissingPluginHandler DO_NOTHING = new DoNothingHandler();
	
	/*
	 * Console
	 */
	public static MissingPluginHandler logToConsole(String... messages)
	{
		return logToConsole(RAW, messages);
	}
	public static MissingPluginHandler logToConsole(MessageStyle style, String... messages) 
	{
		LogToConsoleHandler handler = new LogToConsoleHandler();
		addStyledMessages(handler, style, messages);

		return handler;
	}
	public static MissingPluginHandler logErrorToConsole(String... messages) 
	{
		//"Error" means that the messages become red
		MessageStyle redStyle = new MessageStyle()
				.withFinalTouch(message -> RED + message);

		return logToConsole(redStyle, messages);
	}
	public static MissingPluginHandler logErrorToConsole(MessageStyle style, String... messages) 
	{
		//"Error" means that the messages become red
		MessageStyle redStyle = style.withFinalTouch(message -> RED + message);

		return logToConsole(redStyle, messages);
	}

	/*
	 * java.util.logging.Logger
	 */
	public static MissingPluginHandler log(Logger logger, Level logLevel, MessageStyle style, String... messages) 
	{
		LoggerMessageHandler handler = new LoggerMessageHandler(logger, logLevel);
		addStyledMessages(handler, style, messages);
		
		return handler;
	}
	public static MissingPluginHandler log(Logger logger, Level logLevel, String... messages) 
	{
		return log(logger, logLevel, RAW, messages);
	}


	/*
	 * General
	 */
	public static MissingPluginHandler run(Consumer<PluginHook> action)
	{
		return new ActionHandler(action);
	}
	public static MissingPluginHandler disablePlugin(Plugin plugin) 
	{
		return new DisablePluginHandler(plugin);
	}
	public static MissingPluginHandler handleInOrder(MissingPluginHandler... handlers)
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