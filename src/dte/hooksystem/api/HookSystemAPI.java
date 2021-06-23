package dte.hooksystem.api;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.repository.SimpleHookRepository;
import dte.hooksystem.hooks.service.HookService;
import dte.hooksystem.hooks.service.SimpleHookService;

public class HookSystemAPI 
{
	//Container of API methods
	private HookSystemAPI(){}
	
	/**
	 * Creates a service responsible for the hooks of the given {@code plugin};
	 * Its responsibilities are registering and retrieving the hooks of the plugin.
	 * 
	 * @param owningPlugin The plugin which will own the service.
	 * @return The hooks manager instance of the provided {@code plugin}.
	 */
	public static HookService createHookService(Plugin owningPlugin) 
	{
		return new SimpleHookService(owningPlugin, new SimpleHookRepository());
	}
}