package dte.hooksystem.hooks.repository;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;

public class SimpleHookRepository implements HookRepository
{
	private final Map<Class<? extends PluginHook>, PluginHook> hookByClass = new HashMap<>();
	
	@Override
	public void register(PluginHook hook) 
	{
		Objects.requireNonNull(hook);
		
		this.hookByClass.put(hook.getClass(), hook);
	}
	
	@Override
	public <H extends PluginHook> Optional<H> findHook(Class<H> hookClass)
	{
		PluginHook hook = this.hookByClass.get(hookClass);
		
		return Optional.ofNullable(hook)
				.map(hookClass::cast)
				.filter(PluginHook::isAvailable);
	}
	
	@Override
	public <T> List<T> findHooksOf(Class<T> hookTypeClass)
	{
		Objects.requireNonNull(hookTypeClass);
		
		return this.hookByClass.values().stream()
				.filter(hook -> hookTypeClass.isAssignableFrom(hook.getClass()))
				.map(hookTypeClass::cast)
				.collect(toList());
	}
	
	@Override
	public boolean isHooked(Plugin plugin) 
	{
		String pluginName = Objects.requireNonNull(plugin).getName();
		
		return this.hookByClass.values().stream()
				.anyMatch(hook -> hook.getPluginName().equals(pluginName));
	}
	
	@Override
	public int size()
	{
		return this.hookByClass.size();
	}
	
	@Override
	public Set<PluginHook> getHooksView()
	{
		return new HashSet<>(this.hookByClass.values());
	}
}