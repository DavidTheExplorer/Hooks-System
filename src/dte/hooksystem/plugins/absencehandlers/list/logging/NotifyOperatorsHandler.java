package dte.hooksystem.plugins.absencehandlers.list.logging;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.utils.OnlineOperators;

public class NotifyOperatorsHandler extends MessagerHandler
{
	@Override
	public void handle(PluginHook failedHook)
	{
		String[] messages = createMessagesFor(failedHook);
		
		notifyOperators(messages);
	}
	
	@Override
	public void sendMessage(String message) 
	{
		notifyOperators(message);
	}

	@Override
	public MessagerHandler copy()
	{
		return copyTo(NotifyOperatorsHandler::new);
	}
	
	private void notifyOperators(String... messages) 
	{
		OnlineOperators.forOperators(operator -> operator.sendMessage(messages));
	}
}