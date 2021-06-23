package dte.hooksystem.utils;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.hooksystem.serverplugin.HookSystem;

public class OnlineOperators 
{
	//Container of static methods
	private OnlineOperators(){}
	
	private static Set<Player> onlineOperators;
	
	public static void init() 
	{
		//Read the current amount of online OPs
		onlineOperators = Bukkit.getOperators().stream()
				.filter(OfflinePlayer::isOnline)
				.map(OfflinePlayer::getPlayer)
				.collect(toSet());
		
		//Register the listeners of the amount's modification
		HookSystem.getInstance().registerListeners(new OperatorsUpdateListeners());
	}
	public static void forOperators(Consumer<Player> operatorsAction) 
	{
		verifyInit();
		
		onlineOperators.forEach(operatorsAction::accept);
	}
	public static int size() 
	{
		verifyInit();
		
		return onlineOperators.size();
	}
	public static Set<Player> get()
	{
		verifyInit();
		
		return new HashSet<>(onlineOperators);
	}
	private static void verifyInit() 
	{
		if(OperatorsUpdateListeners.INSTANCE == null)
			throw new IllegalStateException("Cannot access the OnlineOperators class because it wasn't initialized!");
	}
	
	private static class OperatorsUpdateListeners implements Listener
	{
		private static OperatorsUpdateListeners INSTANCE;
		
		public OperatorsUpdateListeners() 
		{
			INSTANCE = this;
		}
		
		@EventHandler
		public void registerOnJoin(PlayerJoinEvent event) 
		{
			handleEvent(event, onlineOperators::add);
		}
		
		@EventHandler
		public void deregisterOnLeave(PlayerQuitEvent event) 
		{
			handleEvent(event, onlineOperators::remove);
		}
		
		private void handleEvent(PlayerEvent event, Consumer<Player> operatorAction) 
		{
			Player player = event.getPlayer();
			
			if(player.isOp())
				operatorAction.accept(player);
		}
	}
}
