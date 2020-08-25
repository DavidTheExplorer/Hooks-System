package com.hooksystem.exampleplugin.hooks;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.hooksystem.base.BasicPluginHook;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class WorldGuardHook extends BasicPluginHook
{
	private RegionContainer regionContainer;
	
	public WorldGuardHook() 
	{
		super("WorldGuard");
	}
	
	@Override
	public void setup() 
	{
		this.regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
	}
	public RegionManager getRegionManager(World world) 
	{
		return this.regionContainer.get(BukkitAdapter.adapt(world));
	}
	public ProtectedRegion getPlayerRegion(Player player) 
	{
		Set<ProtectedRegion> playerRegions = getPlayerRegions(player).getRegions();
		
		return playerRegions.isEmpty() ? null : playerRegions.iterator().next();
	}
	public ApplicableRegionSet getPlayerRegions(Player player) 
	{
		return getRegionsThatContain(player.getLocation());
	}
	public ApplicableRegionSet getRegionsThatContain(Location location)
	{
		BlockVector3 locationVector = BukkitAdapter.asVector(location).toBlockPoint();
		
		return this.getRegionManager(location.getWorld()).getApplicableRegions(locationVector);
	}
}
