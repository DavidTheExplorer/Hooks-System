package dte.hooksystem.hooks;

import java.util.Objects;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * This class provides the basic structure for Hooks Implementations. 
 * <p>
 * All you need to do is to is:
 * <ul>
 * 		<li> super() the constructor with a plugin's name(don't do anything else there!). </li>
 * 		<li> Optionally override the {@code setup()} method for any initializations. </li>
 * 		<li> Now you can add any useful methods you want. </li>
 * </ul>
 */
public abstract class AbstractPluginHook implements PluginHook
{
	private final String pluginName;
	private final Plugin plugin;

	public AbstractPluginHook(String pluginName)
	{
		this.pluginName = pluginName;
		this.plugin = Bukkit.getPluginManager().getPlugin(pluginName);
	}

	@Override
	public String getPluginName()
	{
		return this.pluginName;
	}

	@Override
	public Optional<Plugin> getPlugin() 
	{
		return Optional.ofNullable(this.plugin);
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