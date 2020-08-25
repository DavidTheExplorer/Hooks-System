package com.hooksystem.base;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HooksManager 
{
	//Singleton
	private HooksManager(){}
	private static final HooksManager INSTANCE = new HooksManager();
	public static HooksManager getInstance(){return INSTANCE;}
	
	private final Map<String, IPluginHook> hookByName = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T extends IPluginHook> T findHook(String pluginName) 
	{
		return (T) this.hookByName.get(pluginName);
	}
	public <T> List<T> findHooksOf(Class<T> hookTypeClass)
	{
		return this.hookByName.values().stream()
				.filter(hook -> hookTypeClass.isAssignableFrom(hook.getClass()))
				.map(hookTypeClass::cast)
				.collect(toList());
	}
	public void registerHook(IPluginHook hook)
	{
		registerHook(hook, () -> {});
	}
	public void registerHook(IPluginHook hook, Runnable absenceHandler) 
	{
		if(!hook.isPresent())
		{
			absenceHandler.run();
			return;
		}
		hook.setup();
		this.hookByName.put(hook.getPluginName(), hook);
	}
	public Collection<IPluginHook> getHooksView() 
	{
		return this.hookByName.values();
	}
}