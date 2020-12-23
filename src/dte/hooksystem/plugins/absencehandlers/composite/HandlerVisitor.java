package dte.hooksystem.plugins.absencehandlers.composite;

import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public interface HandlerVisitor
{
	void visit(PluginAbsenceHandler handler);
}