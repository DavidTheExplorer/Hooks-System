package dte.hooksystem.plugins.absencehandlers.list;

import java.util.function.Consumer;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public class ActionHandler implements PluginAbsenceHandler, Consumer<PluginHook>
{
	private final Consumer<PluginHook> action;
	
	public ActionHandler(Consumer<PluginHook> action) 
	{
		this.action = action;
	}
	
	@Override
	public void handle(PluginHook failedHook)
	{
		this.action.accept(failedHook);
	}

	@Override
	public void accept(PluginHook failedHook) 
	{
		handle(failedHook);
	}
}