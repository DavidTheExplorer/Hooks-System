package dte.hooksystem.hooks.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;

public interface HookRepository
{
	/**
	 * Registers a new hook which later can be retrieved by its class.
	 * <p>
	 * Explanation: If the developer tries to register a hook whose plugin is not on the server, it will not be registered(and some MissingPluginHandler will run) - 
	 * So there is no need to specify any plugin name in any method.
	 * 
	 * @param hook The hook to register.
	 */
	void register(PluginHook hook);

	/**
	 * Returns an Optional of the registered instance of the given hook class. 
	 * <p>
	 * If this method returned an Empty Optional, one of the following had happened:
	 * <ul>
	 * 	<li>The hook's plugin is missing in the server.</li>
	 * 	<li>There was an Exception during the hook's <i>init()</i> method.</li>
	 * </ul>
	 * 
	 * @param <H> The type of the hook.
	 * @param hookClass The type of the hook.
	 * @return The registered hook of the given hook class.
	 */
	<H extends PluginHook> Optional<H> findHook(Class<H> hookClass);

	/**
	 * Returns all the hooks that extend the specified parent class.
	 * 
	 * @param <T> The parent class.
	 * @param hookTypeClass The parent class.
	 * @return A list of the hooks that extend the specified parent type.
	 */
	<T> List<T> findHooksOf(Class<T> hookTypeClass);

	/**
	 * Returns the registered hook that extends the provided {@code class}.
	 * <p>
	 * If zero were registered, an Empty Optional is registered; If more than 1 was registered, the provided {@code conflictsHandler} is executed and an Empty Optional is returned.
	 * 
	 * @param <T> The parent class.
	 * @param hookTypeClass The parent class.
	 * @param conflictsHandler What to do if 2 hooks of the provided {@code class} were registered, it accepts the list of the found hooks.
	 * @return The registered hook of the provided {@code hook type}.
	 */
	default <T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler)
	{
		Objects.requireNonNull(conflictsHandler);
		
		List<T> hooksFound = findHooksOf(hookTypeClass);
		
		if(hooksFound.isEmpty())
			return Optional.empty();

		if(hooksFound.size() == 1)
			return Optional.of(hooksFound.get(0));
		
		conflictsHandler.accept(hooksFound);
		return Optional.empty();
	}

	/**
	 * Checks whether this repository contains a hook that is hooked to the given plugin.
	 * 
	 * @param plugin The potentially hooked plugin.
	 * @return Whether this repository has an hook for the given plugin.
	 */
	boolean isHooked(Plugin plugin);

	/**
	 * Returns the amount of registered hooks in this repository.
	 * 
	 * @return The registered hooks amount.
	 */
	int size();

	/**
	 * Returns the amount of registered hooks in this repository, at the time of execution.
	 * 
	 * @return The current registered hooks.
	 */
	Set<PluginHook> getHooksView();
}