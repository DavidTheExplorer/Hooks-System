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
	 * If this method returned false, <b> don't use any of this hook's methods.
	 * @return the existence of the plugin in the server
	 */
	public boolean isPresent();
	
	/** Setup things related to the hook(For example storing the plugin's API class) */
	public default void setup(){}
}
