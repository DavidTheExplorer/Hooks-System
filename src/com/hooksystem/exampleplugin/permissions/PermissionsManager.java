package com.hooksystem.exampleplugin.permissions;

import java.util.UUID;

public interface PermissionsManager
{
	public String getName();
	public String getPlayerGroup(UUID playerUUID); //refactor to return a wrapping Group object
	public boolean groupExists(String groupName);
}
