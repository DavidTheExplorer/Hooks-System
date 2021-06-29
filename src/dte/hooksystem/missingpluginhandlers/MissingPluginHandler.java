package dte.hooksystem.missingpluginhandlers;

import dte.hooksystem.hooks.PluginHook;

@FunctionalInterface
public interface MissingPluginHandler
{
	/**
	 * Runs when the provided {@code hook} couldn't be registered, because the plugin it represents is not on the server.
	 * 
	 * @param failedHook The hook whose plugin is not on the server.
	 */
	void handle(PluginHook failedHook);
}