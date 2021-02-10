package dte.hooksystem.hooks.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;

//TODO: add this method: <T> List<T> supply(Class<? extends Supplier<T>> supplierClass);
public interface IHookRepository
{
	/**
	 * Registers a new hook which can be retrieved by its class.
	 * <p>
	 * A plugin can only have <b>one</b> hook registered for, so there is no need to specify the plugin's name in any method.
	 * 
	 * @param hook The hook to register.
	 */
	void register(PluginHook hook);

	/**
	 * Returns the registered instance of the given hook class. 
	 * <p>
	 * A plugin can only have <b>one</b> hook registered for, so there is no need to specify the plugin's name in any method.
	 * 
	 * @param <H> The type of the hook.
	 * @param hookClass The class of the hook.
	 * @return The registered hook of the given hook class.
	 */
	<H extends PluginHook> Optional<H> findHook(Class<H> hookClass);
	
	/**
	 * Returns all the hooks that are <i>subtype</i> or <i>implement</i> the specified parent type.
	 * 
	 * @param <T> The parent type.
	 * @param hookTypeClass The parent class.
	 * @return A list of the hooks that extend the specified parent type.
	 */
	<T> List<T> findHooksOf(Class<T> hookTypeClass);
	
	/**
	 * Returns the registered hook which is subtype of the provided {@code hook class}; 
	 * If more than 1 was registered, the provided {@code conflictsHandler} is executed.
	 * 
	 * @param <T> The parent type.
	 * @param hookTypeClass The type of the hook.
	 * @param conflictsHandler What to do if 2 hooks of the provided {@code hook class} were registered, it accepts the list of the found hooks.
	 * @return The registered hook of the provided {@code hook type}.
	 */
	<T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler);
	
	/**
	 * Checks whether this registry contains a hook that is hooked to the given plugin.
	 * 
	 * @param plugin The potentially hooked plugin.
	 * @return Whether this registry has an hook for the given plugin.
	 */
	boolean isHooked(Plugin plugin);
	
	/**
	 * Returns the amount of registered hooks in this registry.
	 * 
	 * @return The registerd hooks amount.
	 */
	int size();
	
	/**
	 * Returns a view set of the currently registered hooks in this registry.
	 * 
	 * @return The current registered hooks.
	 */
	Set<PluginHook> getHooksView();
}