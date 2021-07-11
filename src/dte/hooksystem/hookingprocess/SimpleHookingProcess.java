package dte.hooksystem.hookingprocess;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.missingpluginhandlers.MissingPluginHandler;
import dte.hooksystem.service.HookService;

public class SimpleHookingProcess implements HookingProcess<SimpleHookingProcess>
{
	private final PluginHook hook;
	private final HookService hookService;

	public SimpleHookingProcess(PluginHook hook, HookService hookService)
	{
		this.hook = hook;
		this.hookService = hookService;
	}
	
	/**
	 * Finishes by initializing and registering the hook within the service.
	 * <p>
	 * If the plugin the hook represents is not on the server, the provided {@code missingPluginHandler} is executed.
	 * 
	 * @param missingPluginHandler What happens if the plugin the hook represents is not on the server.
	 * @throws PluginAlreadyHookedException If the service already has a hook for the {@code hook}'s plugin.
	 * @throws HookInitException If there was a problem during the hook's {@code init()} method.
	 */
	public void orElse(MissingPluginHandler missingPluginHandler) throws PluginAlreadyHookedException, HookInitException
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin(this.hook.getPluginName());
		
		//if the hook's plugin is missing, call the handler and don't register the hook
		if(plugin == null)
		{
			missingPluginHandler.handle(this.hook);
			return;
		}

		//a plugin can't have 2 different hooks
		if(this.hookService.isHooked(plugin))
			throw new PluginAlreadyHookedException(plugin);
		
		//init the hook
		try
		{
			this.hook.init();
		}
		catch(Exception exception)
		{
			throw new HookInitException(this.hook.getPluginName(), exception);
		}
		
		this.hookService.register(this.hook, missingPluginHandler);
	}
}
