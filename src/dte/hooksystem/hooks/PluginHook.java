package dte.hooksystem.hooks;

/**
 * Represents a safe functionality wrapper of a certain plugin;
 * Implementations should expose <i>public</i> methods - which encapsulates the internal plugin's data.
 * <p>
 * The suggested name for implementations is <i>pluginName</i>Hook (e.g EssentialsHook, WorldEditHook).
 */
public interface PluginHook
{
	/**
	 * Returns the name of the plugin this hook <i>hooks to</i>(or technically just wraps its functionality).
	 * 
	 * @return The hooked plugin's name.
	 */
	String getPluginName();

	/**
	 * Returns whether this hook can be used(e.g. can return false if a certain file is inaccessible).
	 * 
	 * @return Whether this hook is can be used.
	 */
	default boolean isAvailable()
	{
		return true;
	}

	/** 
	 * The only place where it's safe to(and the developer is supposed to) initialize stuff from the plugin's code(<i>e.g.</i> Storing the API instance).
	 * <p>
	 * If an Exception was thrown from this method, it would be caught and be friendly displayed in the Console.
	 * 
	 * @throws Exception if any Exception was thrown.
	 */
	default void init() throws Exception{}
}