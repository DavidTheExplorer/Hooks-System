package dte.hooksystem.missingpluginhandlers;

import java.util.function.Consumer;

import dte.hooksystem.hooks.PluginHook;

public class ConsumerHandler implements MissingPluginHandler
{
	private final Consumer<PluginHook> action;

	public ConsumerHandler(Consumer<PluginHook> action) 
	{
		this.action = action;
	}
	
	@Override
	public void handle(PluginHook failedHook)
	{
		this.action.accept(failedHook);
	}
}