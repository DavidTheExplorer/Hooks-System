package dte.hooksystem.internal;

import java.util.Optional;

import org.bukkit.plugin.Plugin;

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
	public Optional<Plugin> getPlugin() 
	{
		return this.hook.getPlugin();
	}
	
	@Override
	public void init() throws Exception
	{
		this.hook.init();
	}
}
