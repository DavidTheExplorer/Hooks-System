package dte.hooksystem.plugins.absencehandlers;

import dte.hooksystem.hooks.PluginHook;

public interface PluginAbsenceHandler
{
	/**
	 * Runs when the specified {@code hook} couldn't be registered(usually because the plugin isn't on the server)
	 * 
	 * @param failedHook The hook that failed to be registered.
	 */
	void handle(PluginHook failedHook);
	
	PluginAbsenceHandler copy();
}