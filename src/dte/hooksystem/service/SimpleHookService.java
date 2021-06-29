package dte.hooksystem.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.missingpluginhandlers.MissingPluginHandler;

public class SimpleHookService extends AbstractHookService
{
	private final Map<Class<?>, PluginHook> hookByClass = new HashMap<>();

	public SimpleHookService(Plugin owningPlugin)
	{
		super(owningPlugin);
	}
	
	@Override
	public void register(PluginHook hook, MissingPluginHandler missingPluginHandler) throws PluginAlreadyHookedException, HookInitException
	{
		Objects.requireNonNull(hook);
		Objects.requireNonNull(missingPluginHandler);

		Plugin plugin = Bukkit.getPluginManager().getPlugin(hook.getPluginName());

		//if the hook's plugin is missing, call the handler and don't register the hook
		if(plugin == null)
		{
			missingPluginHandler.handle(hook);
			return;
		}

		//a plugin can't have 2 different hooks
		if(isHooked(plugin)) 
			throw new PluginAlreadyHookedException(plugin);

		//init the hook
		try
		{
			hook.init();
		}
		catch(Exception exception)
		{
			throw new HookInitException(hook.getPluginName(), exception);
		}

		//register the hook
		this.hookByClass.put(hook.getClass(), hook);
	}

	@Override
	public <H extends PluginHook> Optional<H> query(Class<H> hookClass) 
	{
		PluginHook hook = this.hookByClass.get(hookClass);

		return Optional.ofNullable(hook)
				.map(hookClass::cast)
				.filter(PluginHook::isAvailable);
	}

	@Override
	public Set<PluginHook> getHooks()
	{
		return new HashSet<>(this.hookByClass.values());
	}
	
	@Override
	public Iterator<PluginHook> iterator() 
	{
		return this.hookByClass.values().iterator();
	}
}