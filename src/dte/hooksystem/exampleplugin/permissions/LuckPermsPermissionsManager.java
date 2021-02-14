package dte.hooksystem.exampleplugin.permissions;

import java.util.UUID;

import dte.hooksystem.exampleplugin.hooks.permissionmanagers.LuckPermsHook;

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
		return this.luckPermsHook.getPlayerGroupName(playerUUID);
	}

	@Override
	public boolean groupExists(String groupName) 
	{
		return this.luckPermsHook.groupExists(groupName);
	}
}