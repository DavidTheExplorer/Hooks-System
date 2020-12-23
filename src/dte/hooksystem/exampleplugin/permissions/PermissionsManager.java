package dte.hooksystem.exampleplugin.permissions;

import java.util.UUID;

public interface PermissionsManager
{
	String getName();
	String getPlayerGroupName(UUID playerUUID); //in reality there would be a getPlayerGroup() method that would return a wrapping Group object
	boolean groupExists(String groupName);
}