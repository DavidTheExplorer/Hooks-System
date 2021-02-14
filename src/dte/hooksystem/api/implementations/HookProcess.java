package dte.hooksystem.api.implementations;

import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.disablePlugin;

import java.util.Objects;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.service.IHookService;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;
import dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory;

//TODO: Add in the repo's desc that although hooks are required by default, It's recommended to call the required() method to avoid unexpected behavior.
//And that a required hook means the plugin will disable itself if the hook is not present.
public class HookProcess
{
	private final IHookService hookService;
	private final PluginHook[] hooksToRegister;
	
	private boolean required = true;
	private PluginAbsenceHandler pluginAbsenceHandler;
	
	public HookProcess(IHookService hookService, PluginHook... hooksToRegister) 
	{
		this.hookService = Objects.requireNonNull(hookService);
		this.hooksToRegister = Objects.requireNonNull(hooksToRegister);
	}
	public HookProcess required() 
	{
		this.required = true;
		return this;
	}
	public HookProcess softdepend()
	{
		this.required = false;
		return this;
	}
	public HookProcess ifPluginAbsent(PluginAbsenceHandler handler)
	{
		Objects.requireNonNull(handler);
		
		if(this.required)
			//if the hooks are required, the provided handler will run before before the plugin is disabled
			this.pluginAbsenceHandler = AbsenceHandlersFactory.handleInOrder(handler, disablePlugin(this.hookService.getOwningPlugin()));
		else
			this.pluginAbsenceHandler = handler;

		return this;
	}
	public void hook()
	{
		for(PluginHook hook : this.hooksToRegister)
			this.hookService.hookTo(hook, this.pluginAbsenceHandler);
	}
}