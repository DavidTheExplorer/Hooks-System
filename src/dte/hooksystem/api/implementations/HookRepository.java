package dte.hooksystem.api.implementations;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.repository.IHookRepository;

public class HookRepository implements IHookRepository
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
		
		if(hook == null)
			return Optional.empty();
		
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
	public <T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler)
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
	
	@Override
	public boolean isHooked(Plugin plugin) 
	{
		Objects.requireNonNull(plugin);
		
		return this.hookByClass.values().stream()
				.anyMatch(hook -> hook.getPluginName().equals(plugin.getName()));
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