package dte.hooksystem.plugins.missinghandlers.list;

import java.util.function.Consumer;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;

public class ActionHandler implements MissingPluginHandler
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
}