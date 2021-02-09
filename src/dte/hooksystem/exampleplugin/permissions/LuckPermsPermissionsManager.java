package dte.hooksystem.exampleplugin.permissions;

import java.util.UUID;

import me.lucko.luckperms.api.LuckPermsApi;

public class LuckPermsPermissionsManager implements PermissionsManager
{
	private final LuckPermsApi luckPermsAPI;
	
	public LuckPermsPermissionsManager(LuckPermsApi luckPerms) 
	{
		this.luckPermsAPI = luckPerms;
	}
	
	@Override
	public String getName() 
	{
		return "LuckPerms' Permissions Manager";
	}

	@Override
	public String getPlayerGroupName(UUID playerUUID) 
	{
		return this.luckPermsAPI.getUserManager().getUser(playerUUID).getPrimaryGroup();
	}

	@Override
	public boolean groupExists(String groupName) 
	{
		return this.luckPermsAPI.getGroup(groupName) != null;
	}
}