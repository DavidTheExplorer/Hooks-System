package dte.hooksystem.plugins.absencehandlers.copyable;

import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;

public interface CopyableHandler<H extends PluginAbsenceHandler>
{
	H copy();
}