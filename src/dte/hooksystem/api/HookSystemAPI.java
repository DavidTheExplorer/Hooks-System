package dte.hooksystem.api;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.service.HookService;
import dte.hooksystem.service.SimpleHookService;

public class HookSystemAPI 
{
	//Container of API methods
	private HookSystemAPI(){}
	
	/**
	 * Creates a service responsible for managing the hooks of the given {@code plugin}.
	 * 
	 * @param owningPlugin The plugin which the created service serves.
	 * @return The hooks manager instance of the provided {@code plugin}.
	 */
	public static HookService createHookService(Plugin owningPlugin) 
	{
		return new SimpleHookService(owningPlugin);
	}
}