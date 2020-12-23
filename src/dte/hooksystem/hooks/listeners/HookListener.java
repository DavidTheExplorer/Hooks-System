package dte.hooksystem.hooks.listeners;

import org.bukkit.plugin.Plugin;

import dte.hooksystem.hooks.PluginHook;

public interface HookListener 
{
	void onHook(Plugin owningPlugin, PluginHook hook);
}