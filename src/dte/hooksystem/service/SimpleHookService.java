package dte.hooksystem.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bukkit.plugin.Plugin;

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
	public void register(PluginHook hook, MissingPluginHandler missingPluginHandler)
	{
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