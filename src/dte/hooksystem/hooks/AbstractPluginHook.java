package dte.hooksystem.hooks;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * This class provides a convenient skeletal implementation for {@link PluginHook}.
 * <p>
 * How to extend:
 * <ul>
 * 	<li>super the constructor with a plugin's name.</li>
 * 	<li>Override {@code init()} if you want to setup the hook(<b>Don't</b> use the constructor to access the plugin's API. this method runs only if the plugin is on the server)</li>
 * 	<li>Add any useful methods you want :)</li>
 * </ul>
 */
public abstract class AbstractPluginHook implements PluginHook
{
	private final String pluginName;

	public AbstractPluginHook(String pluginName)
	{
		this.pluginName = pluginName;
	}

	@Override
	public String getPluginName()
	{
		return this.pluginName;
	}
	
	protected <T> T queryProvider(Class<T> providerClass) 
	{
		RegisteredServiceProvider<T> registration = Bukkit.getServicesManager().getRegistration(providerClass);
		
		return registration == null ? null : registration.getProvider();
	}
	
	@Override
	public int hashCode()
	{
		return getPluginName().hashCode();
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

		PluginHook other = (PluginHook) object;

		return Objects.equals(this.pluginName, other.getPluginName());
	}
}