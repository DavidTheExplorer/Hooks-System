package dte.hooksystem.hooks;

import java.util.Objects;

/**
 * This class provides the basic structure for Hooks Implementations. 
 * <p>
 * Extension Instructions:
 * <ul>
 * 	<li>super the constructor with a plugin's name(don't do anything else there!)</li>
 * 	<li>Optional: Override the {@code init()} method to setup the hook(safe because the method runs after it was verified the plugin exists)</li>
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