package com.hooksystem.exampleplugin.permissions;

import java.util.UUID;

public interface PermissionsManager
{
	public String getName();
	public String getPlayerGroupName(UUID playerUUID); //In reality there would be a getPlayerGroup() method that would return a Wrapping Group Object
	public boolean groupExists(String groupName);
}