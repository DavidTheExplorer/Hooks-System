package dte.hooksystem.hooks;

import java.util.Optional;

import org.bukkit.plugin.Plugin;

/**
 * Represents a safe wrapper of some functionality of a certain plugin. 
 * Implementations should expose *public* methods to be used by the developer - which encapsulates the internal plugin's data.
 * <p>
 * The suggested name for subtypes is <i>pluginName</i>Hook (e.g EssentialsHook, WorldEditHook).
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
	 * Returns whether this hook can be used(e.g. can return false if a file couldn't be accessed).
	 * <p>
	 * This method is *not* supposed to be called outside the library.
	 * 
	 * @return Whether this hook is can be used.
	 */
	default boolean isAvailable()
	{
		return true;
	}
	
	/**
	 * Returns an Optional of the plugin this hook is supposed to hook to, because it might not be on the server.
	 * 
	 * @return An Optional of plugin of this hook.
	 */
	Optional<Plugin> getPlugin();

	/** 
	 * The only place where it's safe(and you're supposed to) to initialize stuff from the plugin's API (<i>e.g.</i> Storing the API instance).
	 * <p>
	 * If an Exception is thrown from this method, it would be caught and be friendly displayed in the Console.
	 * 
	 * @throws Exception if any Exception was thrown.
	 */
	default void init() throws Exception{}
}