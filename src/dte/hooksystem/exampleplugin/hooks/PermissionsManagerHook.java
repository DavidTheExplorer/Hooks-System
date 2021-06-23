package dte.hooksystem.exampleplugin.hooks;

import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.PluginHook;

/**
 * Represents a hook of a Permissions Manager plugin(LuckPerms, GroupManager, etc).
 * <p>
 * Later, we can get this instance by calling:
 * <pre>
 * PermissionsManagerHook hook = hookService
 *     .findHooksOf(PermissionsManagerHook.class, someConflictHandler)
 *     .orElse(null);
 * </pre>
 */
public interface PermissionsManagerHook extends PluginHook
{
	PermissionsManager getPermissionsManager();
}