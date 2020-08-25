package com.hooksystem.base;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class BasicPluginHook implements IPluginHook
{
	private final Plugin plugin;
	
	public BasicPluginHook(String pluginName)
	{
		this.plugin = Bukkit.getPluginManager().getPlugin(pluginName);
	}
	
	@Override
	public String getPluginName() 
	{
		return this.plugin.getName();
	}
	@Override
	public boolean isPresent()
	{
		return this.plugin != null && this.plugin.isEnabled();
	}
	@Override
	public int hashCode() 
	{
		return Objects.hash(this.plugin);
	}
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		BasicPluginHook other = (BasicPluginHook) object;
		
		return Objects.equals(this.plugin.getName(), other.plugin.getName());
	}
}