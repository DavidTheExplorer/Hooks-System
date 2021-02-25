package dte.hooksystem.exampleplugin.hooks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.hooksystem.exampleplugin.permissions.LuckPermsPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.AbstractPluginHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

public class LuckPermsHook extends AbstractPluginHook implements PermissionsManagerHook
{
	private LuckPerms luckPerms;
	private LuckPermsPermissionsManager permissionsManager; //see permissions package
	
	public LuckPermsHook()
	{
		//determine the hook's plugin(by its name)
		super("LuckPerms");
	}
	
	@Override
	public void init() throws Exception
	{
		//This method runs once the library verified that LuckPerms is on the server
		//Since we're inside it - It's safe to access its API
		
		this.luckPerms = queryProvider(LuckPerms.class);
		this.permissionsManager = new LuckPermsPermissionsManager(this);
	}
	
	@Override
	public PermissionsManager getPermissionsManager() 
	{
		return this.permissionsManager;
	}

	/*
	 * Public LuckPerms Wrapper Methods
	 */
	public User getUser(UUID playerUUID) 
	{
		Player player = Bukkit.getPlayer(playerUUID);

		if(player != null)
			return this.luckPerms.getPlayerAdapter(Player.class).getUser(player);
		else
			return this.luckPerms.getUserManager().loadUser(playerUUID).join();
	}
	public boolean groupExists(String groupName) 
	{
		return this.luckPerms.getGroupManager().getGroup(groupName) != null;
	}
}