package dte.hooksystem.exampleplugin.hooks.permissionmanagers;

import java.util.UUID;

import dte.hooksystem.exampleplugin.permissions.LuckPermsPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.AbstractPluginHook;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;

public class LuckPermsHook extends AbstractPluginHook implements PermissionsManagerHook
{
	private LuckPermsApi luckPerms;
	private LuckPermsPermissionsManager permissionsManager;
	
	public LuckPermsHook()
	{
		super("LuckPerms");
	}
	
	@Override
	public void init() throws Exception 
	{
		this.luckPerms = LuckPerms.getApi();
		this.permissionsManager = new LuckPermsPermissionsManager(this);
	}
	
	@Override
	public PermissionsManager getPermissionsManager() 
	{
		return this.permissionsManager;
	}
	public String getPlayerGroupName(UUID playerUUID) 
	{
		return this.luckPerms.getUserManager().getUser(playerUUID).getPrimaryGroup();
	}
	public boolean groupExists(String groupName) 
	{
		return this.luckPerms.getGroup(groupName) != null;
	}
}
