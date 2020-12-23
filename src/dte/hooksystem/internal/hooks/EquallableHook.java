package dte.hooksystem.internal.hooks;

import java.util.Objects;

import dte.hooksystem.hooks.AbstractPluginHook;
import dte.hooksystem.hooks.PluginHook;

public class EquallableHook extends HookDecorator
{
	public EquallableHook(PluginHook hook) 
	{
		super(hook);
	}
	public PluginHook getSourceHook() 
	{
		return this.hook;
	}
	
	public static PluginHook of(PluginHook hook) 
	{
		return hook instanceof AbstractPluginHook ? hook : new EquallableHook(hook);
	}
	
	@Override
	public boolean equals(Object object) 
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(!(object instanceof PluginHook))
			return false;
		
		PluginHook other = (PluginHook) object;
	
		return Objects.equals(this.hook.getPluginName(), other.getPluginName());
	}
	
	@Override
	public int hashCode() 
	{
		return this.hook.getPluginName().hashCode();
	}
}