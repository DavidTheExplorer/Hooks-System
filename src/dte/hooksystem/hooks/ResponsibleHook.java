package dte.hooksystem.hooks;

import dte.hooksystem.missingpluginhandlers.MissingPluginHandler;

/**
 * A hook that decides what to do if the plugin the hook represents is not on the server.
 */
public interface ResponsibleHook extends PluginHook
{
	MissingPluginHandler getMissingPluginHandler();
}
