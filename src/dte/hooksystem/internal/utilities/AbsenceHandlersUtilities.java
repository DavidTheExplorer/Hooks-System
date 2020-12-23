package dte.hooksystem.internal.utilities;

import java.util.ArrayList;
import java.util.List;

import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;
import dte.hooksystem.plugins.absencehandlers.composite.CompositeHandler;

public class AbsenceHandlersUtilities 
{
	//Container of static methods
	private AbsenceHandlersUtilities(){}
	
	public static boolean isInstance(PluginAbsenceHandler handler, Class<?> classz) 
	{
		if(classz.isInstance(handler))
			return true;

		if(handler instanceof CompositeHandler) 
		{
			CompositeHandler compositeHandler = (CompositeHandler) handler;
			
			//recursively search through the composite for an handler that is an instance of the provided class
			return compositeHandler.getHandlersView(false).stream().anyMatch(encapsulatedHandler -> isInstance(encapsulatedHandler, classz));
		}
		return false;
	}
	public static <H extends PluginAbsenceHandler> List<H> cast(PluginAbsenceHandler handler, Class<H> hookClass)
	{
		List<H> classHandlers = new ArrayList<>();
		addClassHandlers(handler, hookClass, classHandlers);
		
		return classHandlers;
	}
	private static <H extends PluginAbsenceHandler> void addClassHandlers(PluginAbsenceHandler handler, Class<H> hookClass, List<H> handlersList)
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

			for(PluginAbsenceHandler encapsulatedHandler : compositeHandler) 
			{
				addClassHandlers(encapsulatedHandler, hookClass, handlersList);
			}
		}
	}
}
