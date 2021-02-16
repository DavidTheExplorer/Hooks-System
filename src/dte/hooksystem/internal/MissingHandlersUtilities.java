package dte.hooksystem.internal;

import java.util.ArrayList;
import java.util.List;

import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;
import dte.hooksystem.plugins.missinghandlers.composite.CompositeHandler;

public class MissingHandlersUtilities 
{
	//Container of static methods
	private MissingHandlersUtilities(){}
	
	public static boolean isInstance(MissingPluginHandler handler, Class<?> classz) 
	{
		if(classz.isInstance(handler))
			return true;
		
		if(handler instanceof CompositeHandler) 
		{
			CompositeHandler compositeHandler = (CompositeHandler) handler;
			
			//recursively search through the composite for an handler that is an instance of the provided class
			return compositeHandler.getHandlersView(false).stream()
					.anyMatch(encapsulatedHandler -> isInstance(encapsulatedHandler, classz));
		}
		return false;
	}
	public static <H extends MissingPluginHandler> List<H> cast(MissingPluginHandler handler, Class<H> hookClass)
	{
		List<H> classHandlers = new ArrayList<>();
		addClassHandlers(handler, hookClass, classHandlers);
		
		return classHandlers;
	}
	private static <H extends MissingPluginHandler> void addClassHandlers(MissingPluginHandler handler, Class<H> hookClass, List<H> handlersList)
	{
		if(hookClass.isInstance(handler)) 
		{
			handlersList.add(hookClass.cast(handler));
			return;
		}

		//recursively search all the nested matching handlers and add them to the list
		if(handler instanceof CompositeHandler)
		{
			CompositeHandler compositeHandler = (CompositeHandler) handler;

			for(MissingPluginHandler encapsulatedHandler : compositeHandler) 
				addClassHandlers(encapsulatedHandler, hookClass, handlersList);
		}
	}
	
	public static Runnable toRunnable(MissingPluginHandler handler) 
	{
		return () -> handler.handle(null);
	}
}
