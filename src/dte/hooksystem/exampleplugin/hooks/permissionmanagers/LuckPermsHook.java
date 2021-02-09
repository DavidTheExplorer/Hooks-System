package dte.hooksystem.exampleplugin.hooks.permissionmanagers;

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
		this.permissionsManager = new LuckPermsPermissionsManager(this.luckPerms);
	}
	
	@Override
	public PermissionsManager getPermissionsManager() 
	{
		return this.permissionsManager;
	}
}