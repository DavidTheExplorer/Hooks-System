package dte.hooksystem.exampleplugin.permissions;

import java.util.UUID;

public class LocalPermissionsManager implements PermissionsManager
{
	@Override
	public String getName() 
	{
		return "Local Permissions Manager";
	}
	
	@Override
	public String getPlayerGroupName(UUID playerUUID) 
	{
		return "Noobs";
	}
	
	@Override
	public boolean groupExists(String groupName) 
	{
		return true;
	}
}