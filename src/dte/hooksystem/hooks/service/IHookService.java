package dte.hooksystem.hooks.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;

public interface IHookService
{
	/**
	 * Registers the provided {@code hook} as <i>supported</i> by the plugin that owns this service, and initializes it.
	 * 
	 * @param hook The hook to register.
	 * @param missingPluginHandler The handler to run if the hook couldn't be registered because its plugin is not on the server.
	 * @throws PluginAlreadyHookedException If this service already has a registered hook for the provided hook's plugin.
	 * @throws HookInitException If there was a problem during the hook's {@code init()} method.
	 */
	void register(PluginHook hook, MissingPluginHandler missingPluginHandler) throws PluginAlreadyHookedException, HookInitException;
	
	/**
	 * The core method of the class, A hook is identified by its class instead of a plugin's name.
	 * <p>
	 * Empty optional means the hook couldn't be registered due to one of the following:
	 * <ul>
	 * 	<li>The hook's plugin is missing in the server.</li>
	 * 	<li>There was an Exception during the hook's <i>init()</i> method.</li>
	 * </ul>
	 * 
	 * @param <H> The hook's type.
	 * @param hookClass The hook's class.
	 * @return An Optional of the corresponding hook of the provided {@code hook class}.
	 */
	<H extends PluginHook> Optional<H> findHook(Class<H> hookClass);
	
	/**
	 * Returns all the hooks that extend the provided {@code class};
	 * Useful when you want to support multiple plugins that provide similar functionality.
	 * 
	 * @param <T> The common type.
	 * @param hookTypeClass The common type.
	 * @return All the hooks that extend the provided class.
	 */
	<T> List<T> findHooksOf(Class<T> hookTypeClass);
	
	/**
	 * Returns an Optional of the hook that extends the provided {@code class}; 
	 * If there are more than 2 registered, the provided {@code conflictsHandler} is executed.
	 * <p>
	 * Useful when you want to support multiple plugins that provide the same service, but only one at a time.
	 * 
	 * @param <T> The hook type.
	 * @param hookTypeClass The hook type.
	 * @param conflictsHandler Runs when more than 1 plugin that extends the provided {@code hook class} are on the server.
	 * @return An optional of the hook that extends the given class, or Empty Optional if either there are zero or more than 1 registered.
	 */
	<T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler);
	
	/**
	 * Returns a copy of this service's hooks, at the time of execution.
	 * 
	 * @return A copy of this service's hooks.
	 */
	Set<PluginHook> getHooksView();
	
	/**
	 * Returns the amount of registered hooks of this service's plugin, at the time of execution.
	 * 
	 * @return How many hooks were registered in this service.
	 */
	int hooksAmount();
	
	/**
	 * Returns the plugin this service serves.
	 * 
	 * @return This service's owning plugin.
	 */
	Plugin getOwningPlugin();
}