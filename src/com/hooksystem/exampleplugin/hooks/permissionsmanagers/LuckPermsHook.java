package com.hooksystem.exampleplugin.hooks.permissionsmanagers;

import java.util.UUID;

import com.hooksystem.base.BasicPluginHook;
import com.hooksystem.exampleplugin.permissions.PermissionsManager;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;

public class LuckPermsHook extends BasicPluginHook implements PermissionsManager
{
	private LuckPermsApi luckPerms;
	
	public LuckPermsHook()
	{
		super("LuckPerms");
	}
	
	@Override
	public String getName()
	{
		return String.format("%s's Permissions Manager", getPluginName());
	}
	@Override
	public void setup() 
	{
		this.luckPerms = LuckPerms.getApi();
	}
	@Override
	public String getPlayerGroupName(UUID playerUUID) 
	{
		return this.luckPerms.getUserManager().getUser(playerUUID).getPrimaryGroup();
	}
	@Override
	public boolean groupExists(String groupName) 
	{
		return this.luckPerms.getGroup(groupName) != null;
	}
}