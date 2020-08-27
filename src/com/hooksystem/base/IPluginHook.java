package com.hooksystem.base;

public interface IPluginHook
{
	/**
	 * Returns the name of the plugin this hook hooks to.
	 * @return the plugin's name
	 */
	public String getPluginName();
	
	/**
	 * Returns whether the plugin is currently in the server and can be used.
	 * <p>
	 * Used <i>internally</i> for registration, you're not supposed to use it.
	 * @return the useability of the plugin
	 */
	public boolean isPresent();
	
	/** Setup things related to the hook(For example storing the plugin's API class) */
	public default void setup(){}
}