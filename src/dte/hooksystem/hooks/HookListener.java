package dte.hooksystem.hooks;

import org.bukkit.plugin.Plugin;

public interface HookListener 
{
	void onHook(Plugin owningPlugin, PluginHook hook);
}