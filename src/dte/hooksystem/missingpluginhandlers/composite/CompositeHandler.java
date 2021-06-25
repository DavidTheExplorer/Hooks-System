package dte.hooksystem.missingpluginhandlers.composite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.missingpluginhandlers.MissingPluginHandler;
import dte.hooksystem.utils.ArrayUtils;

public class CompositeHandler implements MissingPluginHandler, Iterable<MissingPluginHandler>
{
	private final Collection<MissingPluginHandler> handlers;

	private CompositeHandler(Collection<MissingPluginHandler> handlers) 
	{
		this.handlers = handlers;
	}
	public static CompositeHandler of(MissingPluginHandler... handlers) 
	{
		Collection<MissingPluginHandler> finalHandlers = ArrayUtils.toCollection(handlers, LinkedHashSet::new);

		return new CompositeHandler(finalHandlers);
	}
	public void addHandler(MissingPluginHandler handler) 
	{
		this.handlers.add(handler);
	}
	public void removeHandler(MissingPluginHandler handler) 
	{
		this.handlers.remove(handler);
	}

	@Override
	public void handle(PluginHook failedHook)
	{
		for(MissingPluginHandler handler : this.handlers)
			handler.handle(failedHook);
	}

	@Override
	public Iterator<MissingPluginHandler> iterator() 
	{
		return getAllHandlers().iterator();
	}
	
	public Collection<MissingPluginHandler> getHandlersView(boolean deep)
	{
		return Collections.unmodifiableCollection(deep ? getAllHandlers() : this.handlers);
	}

	//the returned list is created by a recursive search for every nested handler within this composite
	private Collection<MissingPluginHandler> getAllHandlers()
	{
		List<MissingPluginHandler> allHandlers = new ArrayList<>();
		addAllHandlers(this, allHandlers);

		return allHandlers;
	}
	private static void addAllHandlers(MissingPluginHandler currentHandler, Collection<MissingPluginHandler> handlersList)
	{
		if(!(currentHandler instanceof CompositeHandler))
		{
			handlersList.add(currentHandler);
			return;
		}
		CompositeHandler compositeHandler = (CompositeHandler) currentHandler;

		for(MissingPluginHandler encapsulatedHandler : compositeHandler.handlers) 
			addAllHandlers(encapsulatedHandler, handlersList);
	}
}