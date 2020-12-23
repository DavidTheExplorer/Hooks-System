package dte.hooksystem.plugins.absencehandlers.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public class CompositeHandler implements PluginAbsenceHandler, Iterable<PluginAbsenceHandler>
{
	private final Collection<PluginAbsenceHandler> handlers;

	private CompositeHandler(Collection<PluginAbsenceHandler> handlers) 
	{
		this.handlers = handlers;
	}
	public static CompositeHandler of(CompositeHandlerOptions options, PluginAbsenceHandler... handlers) 
	{
		List<PluginAbsenceHandler> handlersList = Arrays.asList(handlers);
		Collection<PluginAbsenceHandler> finalHandlers = options.usesFIFO() ? new LinkedHashSet<>(handlersList) : new HashSet<>(handlersList);

		return new CompositeHandler(finalHandlers);
	}
	public Collection<PluginAbsenceHandler> getHandlersView(boolean deep)
	{
		return Collections.unmodifiableCollection(deep ? getAllHandlers() : this.handlers);
	}
	public void addHandler(PluginAbsenceHandler handler) 
	{
		this.handlers.add(handler);
	}
	public void removeHandler(PluginAbsenceHandler handler) 
	{
		this.handlers.remove(handler);
	}
	
	@Override
	public void handle(PluginHook failedHook) 
	{
		for(PluginAbsenceHandler handler : this.handlers)
		{
			handler.handle(failedHook);
		}
	}

	@Override
	public Iterator<PluginAbsenceHandler> iterator() 
	{
		return getAllHandlers().iterator();
	}

	/*@Override
	public void accept(HandlerVisitor visitor) 
	{
		this.handlers.forEach(visitor::visit);
		visitor.visit(this);
	}*/
	
	//the returned list is created by a recursive search for every nested handler within this composite
	private Collection<PluginAbsenceHandler> getAllHandlers()
	{
		List<PluginAbsenceHandler> allHandlers = new ArrayList<>();
		addAllHandlers(this, allHandlers);
		
		return allHandlers;
	}
	private static void addAllHandlers(PluginAbsenceHandler currentHandler, Collection<PluginAbsenceHandler> handlersList)
	{
		if(!(currentHandler instanceof CompositeHandler))
		{
			handlersList.add(currentHandler);
			return;
		}
		CompositeHandler compositeHandler = (CompositeHandler) currentHandler;
		
		for(PluginAbsenceHandler encapsulatedHandler : compositeHandler.handlers) 
		{
			addAllHandlers(encapsulatedHandler, handlersList);
		}
	}
}