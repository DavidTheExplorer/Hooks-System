package dte.hooksystem.hooks.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.repository.IHookRepository;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;

public class HookService implements IHookService
{
	private final Plugin owningPlugin;
	private final IHookRepository hookRepository;
	
	public HookService(Plugin owningPlugin, IHookRepository hookRepository)
	{
		this.owningPlugin = Objects.requireNonNull(owningPlugin);
		this.hookRepository = Objects.requireNonNull(hookRepository);
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
		if(this.hookRepository.isHooked(plugin)) 
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
		this.hookRepository.register(hook);
	}

	@Override
	public <H extends PluginHook> Optional<H> findHook(Class<H> hookClass)
	{
		return this.hookRepository.findHook(hookClass);
	}

	@Override
	public <T> List<T> findHooksOf(Class<T> hookTypeClass)
	{
		return this.hookRepository.findHooksOf(hookTypeClass);
	}

	@Override
	public <T> Optional<T> findHookOf(Class<T> hookTypeClass, Consumer<List<T>> conflictsHandler)
	{
		return this.hookRepository.findHookOf(hookTypeClass, conflictsHandler);
	}

	@Override
	public Set<PluginHook> getHooksView()
	{
		return this.hookRepository.getHooksView();
	}

	@Override
	public int hooksAmount() 
	{
		return this.hookRepository.size();
	}
	
	@Override
	public Plugin getOwningPlugin() 
	{
		return this.owningPlugin;
	}
}