package dte.hooksystem.plugins.absencehandlers.list.logging;

import static java.util.stream.Collectors.toSet;

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

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.serverplugin.Main;

public class NotifyOperatorsHandler extends MessagerHandler
{
	private static final Set<Player> ONLINE_OPERATORS;

	static
	{
		ONLINE_OPERATORS = Bukkit.getOperators().stream()
				.filter(OfflinePlayer::isOnline)
				.map(OfflinePlayer::getPlayer)
				.collect(toSet());
		
		Bukkit.getPluginManager().registerEvents(new OperatorsUpdateListeners(), Main.getInstance());
	}

	@Override
	public void handle(PluginHook failedHook)
	{
		String[] messages = createMessagesFor(failedHook);

		ONLINE_OPERATORS.forEach(player -> player.sendMessage(messages));
	}

	@Override
	public void sendMessage(String message) 
	{
		ONLINE_OPERATORS.forEach(player -> player.sendMessage(message));
	}

	@Override
	public MessagerHandler copy() 
	{
		return copyMessagesTo(NotifyOperatorsHandler::new);
	}

	private static class OperatorsUpdateListeners implements Listener
	{
		@EventHandler
		public void registerOnJoin(PlayerJoinEvent event) 
		{
			ifOp(event, ONLINE_OPERATORS::add);
		}

		@EventHandler
		public void deregisterOnLeave(PlayerQuitEvent event) 
		{
			ifOp(event, ONLINE_OPERATORS::remove);
		}
		
		private void ifOp(PlayerEvent event, Consumer<Player> playerAction) 
		{
			Player player = event.getPlayer();
			
			if(player.isOp())
				playerAction.accept(player);
		}
	}
	
}