package dte.hooksystem.internal;

import dte.hooksystem.hooks.PluginHook;

public class HookDecorator implements PluginHook
{
	protected final PluginHook hook;
	
	public HookDecorator(PluginHook hook) 
	{
		this.hook = hook;
	}
	
	@Override
	public String getPluginName() 
	{
		return this.hook.getPluginName();
	}
	
	@Override
	public void init() throws Exception
	{
		this.hook.init();
	}
}
