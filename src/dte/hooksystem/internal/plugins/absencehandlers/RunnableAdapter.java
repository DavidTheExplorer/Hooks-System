package dte.hooksystem.internal.plugins.absencehandlers;

import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public class RunnableAdapter
{
	public static Runnable from(PluginAbsenceHandler handler) 
	{
		return () -> handler.handle(null);
	}
}