package dte.hooksystem.exampleplugin.hooks.permissionmanagers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.hooksystem.exampleplugin.permissions.LuckPermsPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.ServicedHook;
import net.luckperms.api.LuckPerms;

public class LuckPermsHook extends ServicedHook<LuckPerms> implements PermissionsManagerHook
{
	private LuckPermsPermissionsManager permissionsManager;
	
	public LuckPermsHook()
	{
		super("LuckPerms", LuckPerms.class);
	}
	
	@Override
	public void init() throws Exception
	{
		super.init();
		this.permissionsManager = new LuckPermsPermissionsManager(this);
	}
	
	@Override
	public PermissionsManager getPermissionsManager() 
	{
		return this.permissionsManager;
	}
	
	public String getPlayerGroupName(UUID playerUUID) 
	{
		Player player = Bukkit.getPlayer(playerUUID);

		if(player != null)
			return this.serviced.getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup();
		else
			return this.serviced.getUserManager().loadUser(playerUUID).join().getPrimaryGroup();
	}
	public boolean groupExists(String groupName) 
	{
		return this.serviced.getGroupManager().getGroup(groupName) != null;
	}
}