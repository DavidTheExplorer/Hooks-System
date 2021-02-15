package dte.hooksystem.hooks.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public interface IHookService
{
	Plugin getOwningPlugin();
	
	/**
	 * Registers the provided {@code hook} as supported by the plugin that owns this service, and initializes it.
	 * 
	 * @param hook The hook to register.
	 * @param handler The handler to run when a hook cannot be registered because its plugin is not on the server.
	 * @throws PluginAlreadyHookedException If this service already has a registered hook for the provided hook's plugin.
	 * @throws HookInitException If there was a problem during the hook's {@code init()} method.
	 */
	void register(PluginHook hook, PluginAbsenceHandler handler) throws PluginAlreadyHookedException, HookInitException;
	
	<H extends PluginHook> Optional<H> findHook(Class<H> hookClass);
	<T> List<T> findHooksOf(Class<T> hookTypeClass);
	<T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler);
	
	Set<PluginHook> getHooksView();
	int hooksAmount();
}
