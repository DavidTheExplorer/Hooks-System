package dte.hooksystem.api;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.api.implementations.HookProcess;
import dte.hooksystem.api.implementations.HookRepository;
import dte.hooksystem.api.implementations.HookService;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.service.IHookService;

public class HookSystemAPI 
{
	//Container of API methods
	private HookSystemAPI(){}
	
	public static IHookService createHookService(Plugin owningPlugin) 
	{
		return new HookService(owningPlugin, new HookRepository());
	}
	public static HookProcess safeMultiHooking(IHookService hookService, PluginHook... hooksToRegister) 
	{
		return new HookProcess(hookService, hooksToRegister);
	}
}