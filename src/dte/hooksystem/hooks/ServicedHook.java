package dte.hooksystem.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ServicedHook<T> extends AbstractPluginHook
{
	private final RegisteredServiceProvider<T> provider;
	
	protected T serviced;
	
	public ServicedHook(String pluginName, Class<T> providerClass)
	{
		super(pluginName);
		
		//TODO: change to load()
		this.provider = Bukkit.getServicesManager().getRegistration(providerClass);
	}
	
	@Override
	public boolean isAvailable() 
	{
		return this.provider != null;
	}
	
	@Override
	public void init() throws Exception 
	{
		this.serviced = this.provider.getProvider();
	}
}