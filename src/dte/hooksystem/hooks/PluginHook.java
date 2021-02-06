package dte.hooksystem.hooks;

import java.util.Optional;

import org.bukkit.plugin.Plugin;

/**
 * Represents a safe wrapper of some functionality of a certain plugin. 
 * Implementations should expose public method to be used by the developer, so all the internal plugin's data are encapsulated.
 * <p>
 * The suggested name for subtypes is <i>pluginName</i>Hook (e.g WorldEditHook, PermissionsEXHook).
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
	 * Returns whether this hook can be used(usually checks that the hooked plugin exists on the server + is enabled).
	 * <p>
	 * Used <i>internally</i> for registration - The developer is not supposed to call this method.
	 * 
	 * @return Whether this hook is can be used.
	 */
	boolean isPresent();
	
	/**
	 * Returns an optional of the plugin this hook is supposed to hook to.
	 * 
	 * @return An optional of plugin of this hook.
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