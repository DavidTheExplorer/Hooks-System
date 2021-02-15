package dte.hooksystem.exampleplugin.hooks;

import dte.hooksystem.exampleplugin.permissions.PermissionsManager;

/**
 * Marks the implementing hook as a Permissions Manager Hook(such as LuckPerms, GroupManager, etc).
 * <p>
 * This class is how we combine the plugin's PermissionsManager interface with the Hooks Library; It enables us to call: 
 * <pre>
 * PermissionsManager manager = hookService
 *     .findHooksOf(PermissionsManagerHook.class, someConflictHandler)
 *     .orElse(null);
 * </pre>
 */
public interface PermissionsManagerHook
{
	PermissionsManager getPermissionsManager();
}