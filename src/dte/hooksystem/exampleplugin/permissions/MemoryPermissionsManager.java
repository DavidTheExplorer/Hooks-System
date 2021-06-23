package dte.hooksystem.exampleplugin.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
		return this.groupsMembers.entrySet().stream()
				.filter(entry -> entry.getValue().contains(playerUUID))
				.findFirst()
				.map(Entry::getKey)
				.orElse(null);
	}
	
	@Override
	public boolean groupExists(String groupName) 
	{
		return this.groupsMembers.containsKey(groupName);
	}
}