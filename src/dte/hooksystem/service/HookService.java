package dte.hooksystem.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.missingpluginhandlers.MissingPluginHandler;

public interface HookService extends Iterable<PluginHook>
{
	/**
	 * Returns the plugin this service serves.
	 * 
	 * @return This service's owning plugin.
	 */
	Plugin getOwningPlugin();
	
	/**
	 * Registers the provided {@code hook} as supported by this service's owning plugin, and initializes it.
	 * <p>
	 * The hook can later be retrieved by its class.
	 * 
	 * @param hook The hook to register.
	 * @param missingPluginHandler What happens when the plugin the hook represents is not on the server.
	 * @throws PluginAlreadyHookedException If this service has a hook for the provided {@code hook}'s plugin.
	 * @throws HookInitException If there was a problem during the hook's {@code init()} method.
	 */
	void register(PluginHook hook, MissingPluginHandler missingPluginHandler) throws PluginAlreadyHookedException, HookInitException;

	/**
	 * Returns an Optional of the registered instance of the provided {@code hook class}.
	 * <p>
	 * Empty Optional is returned in the following cases:
	 * <ul>
	 * 	<li>The plugin the registered hook represents is not on the server.</li>
	 * 	<li>The hook's plugin is on the server, but the hook is not available.
	 * 	<li>an Exception was thrown during the registered hook's <i>init()</i> method.</li>
	 * </ul>
	 * 
	 * @param <H> The type of the returned hook.
	 * @param hookClass The class of the returned hook.
	 * @return An Optional of the registered hook of the provided {@code hook class}.
	 * @see PluginHook#isAvailable()
	 */
	<H extends PluginHook> Optional<H> query(Class<H> hookClass);

	/**
	 * Returns all the registered hooks that extend the provided {@code parent};
	 * Useful when you want to support multiple plugins that provide the same functionality.
	 * 
	 * @param <T> The parent type.
	 * @param parentClass The parent class.
	 * @return The registered hooks that extend the specified {@code parent} class.
	 */
	<T> List<T> queryAll(Class<T> parent);

	/**
	 * Returns an Optional of registered hook that extends the provided {@code parent};
	 * If 2 or more were registered, the provided {@code conflictsHandler} is executed.
	 * <p>
	 * Useful when you want to support multiple plugins that provide the same service, but only one at a time.
	 * <p>
	 * Empty Optional is returned when None OR Multiple hooks of the provided {@code parent} were registered.
	 * 
	 * @param <T> The parent type.
	 * @param parent The parent class.
	 * @param conflictHandler What to do if multiple hooks of the provided {@code class} were registered, accepts the list of the found hooks.
	 * @return The registered hook of the provided {@code parent}.
	 */
	<T> Optional<T> safeQuery(Class<T> parent, Consumer<List<T>> conflictHandler);

	/**
	 * Returns an Optional of registered hook that extends the provided {@code parent};
	 * If multiple hooks were registered, the provided {@code conflictResolver} accepts them and decides which one to return.
	 * <p>
	 * Empty Optional is returned if no instances of the provided {@code parent} were registered.
	 * 
	 * @param <T> The parent type.
	 * @param parent The parent's class.
	 * @param conflictResolver What to return if multiple hooks of the provided {@code class} were registered, accepts the list of the found hooks.
	 * @return The registered hook of the provided {@code parent}.
	 */
	<T> Optional<T> query(Class<T> parent, Function<List<T>, T> conflictResolver); 

	/**
	 * Returns the currently registered hooks inside this repository.
	 * 
	 * @return The currently registered hooks.
	 */
	Set<PluginHook> getHooks();


	/**
	 * Returns the amount of registered hooks in this service.
	 * 
	 * @return The registered hooks amount.
	 */
	int size();
}