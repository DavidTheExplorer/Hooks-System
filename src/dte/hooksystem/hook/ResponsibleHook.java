package dte.hooksystem.hook;

import dte.hooksystem.missingpluginhandler.MissingPluginHandler;

/**
 * A hook that decides what to do if the plugin the hook represents is not on the server.
 */
public interface ResponsibleHook extends PluginHook
{
	MissingPluginHandler getMissingPluginHandler();
}
