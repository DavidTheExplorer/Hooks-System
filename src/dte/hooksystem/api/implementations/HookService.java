package dte.hooksystem.api.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.hooks.repository.IHookRepository;
import dte.hooksystem.hooks.service.IHookService;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

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
	public void hookTo(PluginHook hook, PluginAbsenceHandler pluginAbsenceHandler) throws PluginAlreadyHookedException, HookInitException
	{
		Objects.requireNonNull(hook);
		Objects.requireNonNull(pluginAbsenceHandler);

		//if the hook's plugin is absent, call the handler and don't register the hook
		Optional<Plugin> pluginHolder = hook.getPlugin();

		if(!pluginHolder.isPresent())
		{
			pluginAbsenceHandler.handle(hook);
			return;
		}
		Plugin plugin = pluginHolder.get();

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

		//Register the hook
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
	public Plugin getOwningPlugin() 
	{
		return this.owningPlugin;
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
}