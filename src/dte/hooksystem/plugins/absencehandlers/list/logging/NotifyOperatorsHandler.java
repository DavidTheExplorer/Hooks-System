package dte.hooksystem.plugins.absencehandlers.list.logging;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.serverplugin.Main;

public class NotifyOperatorsHandler extends MessagerHandler
{
	private static final Set<Player> ONLINE_OPERATORS = getOnlineOperators();

	static
	{
		Bukkit.getPluginManager().registerEvents(new OperatorsUpdateListeners(), Main.getInstance());
	}

	public NotifyOperatorsHandler(String[] messages)
	{
		super(messages);
	}

	@Override
	public void handle(PluginHook failedHook)
	{
		String[] messages = createMessagesFor(failedHook);

		ONLINE_OPERATORS.forEach(player -> player.sendMessage(messages));
	}

	@Override
	public void logMessage(String message) 
	{
		ONLINE_OPERATORS.forEach(player -> player.sendMessage(message));
	}

	@Override
	public MessagerHandler copy() 
	{
		return new NotifyOperatorsHandler(getCopiedMessages());
	}
	private static Set<Player> getOnlineOperators()
	{
		return Bukkit.getOperators().stream()
				.filter(OfflinePlayer::isOnline)
				.map(OfflinePlayer::getPlayer)
				.collect(toSet());
	}

	private static class OperatorsUpdateListeners implements Listener
	{
		@EventHandler
		public void registerOnJoin(PlayerJoinEvent event) 
		{
			Player player = event.getPlayer();

			if(player.isOp()) 
			{
				ONLINE_OPERATORS.add(player);
			}
		}

		@EventHandler
		public void deregisterOnLeave(PlayerQuitEvent event) 
		{
			Player player = event.getPlayer();

			if(player.isOp()) 
			{
				ONLINE_OPERATORS.remove(player);
			}
		}
	}
}