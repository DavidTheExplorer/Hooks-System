package dte.hooksystem.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.service.HookService;
import dte.hooksystem.service.SimpleHookService;

public class HookSystemAPI 
{
	//Container of API methods
	private HookSystemAPI(){}
	
	private static final Map<Plugin, HookService> PLUGINS_SERVICES = new HashMap<>();
	
	/**
	 * Returns a cached service responsible for managing the hooks of the given {@code plugin}.
	 * 
	 * @param owningPlugin The plugin which the returned service is responsible for.
	 * @return The hooks manager of the provided {@code plugin}.
	 */
	public static HookService getService(Plugin owningPlugin) 
	{
		return PLUGINS_SERVICES.computeIfAbsent(owningPlugin, SimpleHookService::new);
	}
}