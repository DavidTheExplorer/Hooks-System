package dte.hooksystem.exampleplugin.permissions;

import java.util.UUID;

import dte.hooksystem.exampleplugin.hooks.LuckPermsHook;

/*
 * This is an example of what a plugin that supports multiple permissions managers would have.
 * The entire functionality of this class depends on LuckPerms' hook.
 */
public class LuckPermsPermissionsManager implements PermissionsManager
{
	private final LuckPermsHook luckPermsHook;
	
	public LuckPermsPermissionsManager(LuckPermsHook luckPermsHook) 
	{
		this.luckPermsHook = luckPermsHook;
	}

	@Override
	public String getName() 
	{
		return "LuckPerms' Permissions Manager";
	}

	@Override
	public String getPlayerGroupName(UUID playerUUID) 
	{
		return this.luckPermsHook.getUser(playerUUID).getPrimaryGroup();
	}

	@Override
	public boolean groupExists(String groupName) 
	{
		return this.luckPermsHook.groupExists(groupName);
	}
}