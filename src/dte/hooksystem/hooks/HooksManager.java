package dte.hooksystem.hooks;

import static dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory.disablePlugin;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import dte.hooksystem.exceptions.HookInitException;
import dte.hooksystem.exceptions.PluginAlreadyHookedException;
import dte.hooksystem.hooks.listeners.HookListener;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;
import dte.hooksystem.plugins.absencehandlers.factory.AbsenceHandlersFactory;
import dte.hooksystem.serverplugin.Main;

public class HooksManager
{
	private final Plugin owningPlugin;
	private final Map<Class<? extends PluginHook>, PluginHook> hookByClass = new HashMap<>();
	private final Set<HookListener> hookListeners;

	private PluginAbsenceHandler pluginAbsenceHandler = AbsenceHandlersFactory.DO_NOTHING;

	public HooksManager(Plugin owningPlugin)
	{
		this.owningPlugin = Objects.requireNonNull(owningPlugin);
		this.hookListeners = Sets.newHashSet(Main.getInstance());
	}

	/*
	 * Hooks Retrieval
	 */

	/**
	 * Finds the registered instance of the given hook class. 
	 * <p>
	 * A plugin can only have <b>one</b> hook registered for, so there is no need to specify the plugin's name in any method.
	 * 
	 * @param <H> The type of the hook.
	 * @param hookClass The class of the hook.
	 * @return The registered hook of the given hook class.
	 */
	public <H extends PluginHook> Optional<H> findHook(Class<H> hookClass)
	{
		PluginHook registeredHook = this.hookByClass.get(hookClass);

		return Optional.ofNullable(registeredHook)
				.map(hookClass::cast);
		//.filter(PluginHook::isPresent); TODO: Maybe allow rechecking if the hook exists after some time?
	}

	/**
	 * Returns all the hooks that are <i>subtype</i> or <i>implement</i> the specified parent type.
	 * 
	 * @param <T> The parent type.
	 * @param hookTypeClass The parent class.
	 * @return A list of the hooks that extend the specified parent type.
	 */
	public <T> List<T> findHooksOf(Class<T> hookTypeClass)
	{
		Objects.requireNonNull(hookTypeClass);

		return this.hookByClass.values().stream()
				.filter(hook -> hookTypeClass.isAssignableFrom(hook.getClass()))
				.map(hookTypeClass::cast)
				.collect(toList());
	}

	/**
	 * Returns a hook which is a subtype of the provided {@code hook class}; 
	 * If more than 1 is registered, the provided {@code conflictsHandler} is executed.
	 * 
	 * @param <T> The parent type.
	 * @param hookTypeClass The type of the hook.
	 * @param conflictsHandler The action to run if 2 hooks of the provided {@code hook class} were registered.
	 * @return The registered hook of the provided {@code hook type}.
	 */
	public <T> Optional<T> findHookOf(Class<T> hookTypeClass, Runnable conflictsHandler)
	{
		List<T> hooksFound = findHooksOf(hookTypeClass);

		if(hooksFound.isEmpty())
			return Optional.empty();

		if(hooksFound.size() == 1)
			return Optional.of(hooksFound.get(0));

		conflictsHandler.run();
		return Optional.empty();
	}

	/*
	 * Hooks Registration
	 */

	/**
	 * Sets the default handler to run when a hook cannot be registered because the hook's plugin is not on the server.
	 * 
	 * @param handler The default hook's plugin absence handler.
	 */
	public void setAbsentPluginHandler(PluginAbsenceHandler handler) 
	{
		this.pluginAbsenceHandler = Objects.requireNonNull(handler);
	}
	public HookProcess startHookingTo(PluginHook... hooks)
	{
		return new HookProcess(Objects.requireNonNull(hooks));
	}
	private void registerHook(PluginHook hook, HookProcess process)
	{
		String pluginName = hook.getPluginName();

		//a plugin can't have 2 different hooks
		if(isHookedTo(pluginName))
			throw new PluginAlreadyHookedException(pluginName);

		//if the hook is absent(the hook's plugin wasn't found, the hook couldn't access a file, etc)
		//call the handler and don't register the hook
		if(!hook.isPresent())
		{
			process.pluginAbsenceHandler.handle(hook);
			return;
		}

		//init the hook
		try
		{
			hook.init();
		} 
		catch(Exception exception)
		{
			throw new HookInitException(hook.getPluginName(), exception);
		}
		unsafeHook(hook);
	}
	private void unsafeHook(PluginHook hook)
	{
		this.hookByClass.put(hook.getClass(), hook);

		//notify the hook listeners
		this.hookListeners.forEach(listener -> listener.onHook(this.owningPlugin, hook));
	}

	/*
	 * General
	 */
	public boolean isHookedTo(String pluginName) 
	{
		return this.hookByClass.values().stream()
				.anyMatch(hook -> hook.getPluginName().equals(pluginName));
	}
	public int hooksAmount()
	{
		return this.hookByClass.size();
	}
	public Set<Plugin> getHookedPlugins()
	{
		return this.hookByClass.values().stream()
				.filter(hook -> hook.getPlugin().isPresent())
				.map(hook -> hook.getPlugin().get())
				.collect(toSet());
	}
	public Set<PluginHook> getHooksView()
	{
		return new HashSet<>(this.hookByClass.values());
	}

	public class HookProcess
	{
		private final PluginHook[] hooksToRegister;
		private boolean required = true;
		private PluginAbsenceHandler pluginAbsenceHandler = HooksManager.this.pluginAbsenceHandler;

		HookProcess(PluginHook[] hooksToRegister) 
		{
			this.hooksToRegister = hooksToRegister;
		}

		//hooks are required by default, but it's recommended to call this method anyway to avoid unwanted behavior.
		public HookProcess required() 
		{
			this.required = true;
			return this;
		}
		public HookProcess optional()
		{
			this.required = false;
			return this;
		}
		public HookProcess ifPluginAbsent(PluginAbsenceHandler handler)
		{
			Objects.requireNonNull(handler);
			
			if(this.required)
				//if the hooks are required, the provided handler will run before before the plugin is disabled
				this.pluginAbsenceHandler = AbsenceHandlersFactory.handleOrdered(handler, disablePlugin(HooksManager.this.owningPlugin));
			else 
				this.pluginAbsenceHandler = handler;

			return this;
		}
		public void hook()
		{
			for(PluginHook hook : this.hooksToRegister) 
			{
				registerHook(hook, this);
			}
		}
	}
}