package dte.hooksystem.api;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.repository.HookRepository;
import dte.hooksystem.hooks.service.HookService;
import dte.hooksystem.hooks.service.IHookService;

public class HookSystemAPI 
{
	//Container of API methods
	private HookSystemAPI(){}
	
	public static IHookService createHookService(Plugin owningPlugin) 
	{
		return new HookService(owningPlugin, new HookRepository());
	}
}