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
	 * Registers the provided {@code hook} as supported by the plugin that owns this service, and tries to initialize it.
	 * <p>
	 * If the plugin the hook represents is not on the server, the provided {@code missingPluginHandler} is executed.
	 * <p>
	 * The hook can later be retrieved by its class.
	 * 
	 * @param hook The hook to register.
	 * @param missingPluginHandler What happens if the plugin the hook represents is not on the server.
	 * @throws PluginAlreadyHookedException If this service already has a hook for the {@code hook}'s plugin.
	 * @throws HookInitException If there was a problem during the hook's {@code init()} method.
	 */
	void register(PluginHook hook, MissingPluginHandler missingPluginHandler) throws PluginAlreadyHookedException, HookInitException;

	/**
	 * Returns an Optional of the registered hook of the provided {@code hook class}.
	 * <p>
	 * Empty Optional is returned in the following cases:
	 * <ul>
	 * 	<li>The plugin the hook represents is not on the server.</li>
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
	 * Returns all the registered hooks that extend the provided {@code parent}.
	 * <p>
	 * Useful when you want to treat multiple plugins that provide the same functionality.
	 * 
	 * @param <T> The parent type.
	 * @param parent The parent class.
	 * @return All registered hooks that extend the specified {@code parent}.
	 */
	<T> List<T> queryAll(Class<T> parent);

	/**
	 * Returns an Optional of the registered hook that extend the provided {@code parent};
	 * If multiple were registered, the provided {@code conflictHandler} is executed and Empty Optional is returned.
	 * <p>
	 * Useful when you want to support multiple plugins that provide the same functionality, <b>but</b> only one at a time.
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
	 * 
	 * @param <T> The parent type.
	 * @param parent The parent's class.
	 * @param conflictResolver What to return if multiple hooks of the provided {@code class} were registered, accepts the list of the found hooks.
	 * @return The registered hook of the provided {@code parent}.
	 */
	<T> Optional<T> query(Class<T> parent, Function<List<T>, T> conflictResolver); 

	/**
	 * Returns the currently registered hooks inside this service.
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