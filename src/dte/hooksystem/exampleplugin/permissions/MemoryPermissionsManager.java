package dte.hooksystem.exampleplugin.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MemoryPermissionsManager implements PermissionsManager
{
	private final Map<String, Set<UUID>> groupsMembers = new HashMap<>();
	
	@Override
	public String getName() 
	{
		return "Local Permissions Manager";
	}
	
	@Override
	public String getPlayerGroupName(UUID playerUUID) 
	{
		for(Map.Entry<String, Set<UUID>> entry : this.groupsMembers.entrySet()) 
		{
			String groupName = entry.getKey();
			Set<UUID> groupMembers = entry.getValue();
			
			if(groupMembers.contains(playerUUID))
				return groupName;
		}
		return null;
	}
	
	@Override
	public boolean groupExists(String groupName) 
	{
		return this.groupsMembers.containsKey(groupName);
	}
}