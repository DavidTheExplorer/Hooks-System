package dte.hooksystem.plugins.missinghandlers;

import dte.hooksystem.hooks.PluginHook;

public interface MissingPluginHandler
{
	/**
	 * Runs when the specified {@code hook} couldn't be registered because its plugin is missing in the server.
	 * 
	 * @param failedHook The hook that failed to be registered.
	 */
	void handle(PluginHook failedHook);
}