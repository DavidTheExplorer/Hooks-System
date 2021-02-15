package dte.hooksystem.exampleplugin.hooks.permissionmanagers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.hooksystem.exampleplugin.permissions.LuckPermsPermissionsManager;
import dte.hooksystem.exampleplugin.permissions.PermissionsManager;
import dte.hooksystem.hooks.ServicedHook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

public class LuckPermsHook extends ServicedHook<LuckPerms> implements PermissionsManagerHook
{
	private LuckPermsPermissionsManager permissionsManager;

	public LuckPermsHook()
	{
		//determine the hook's plugin(by its name) and the service's class
		super("LuckPerms", LuckPerms.class);
	}

	@Override
	public void init() throws Exception
	{
		//This method runs once the library verified that LuckPerms is on the server
		//Since we're inside it - It's safe to access its API
		
		super.init(); //inits the LuckPerms object from Bukkit's ServicesManager 
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
			return this.serviced.getPlayerAdapter(Player.class).getUser(player);
		else
			return this.serviced.getUserManager().loadUser(playerUUID).join();
	}
	public boolean groupExists(String groupName) 
	{
		return this.serviced.getGroupManager().getGroup(groupName) != null;
	}
}