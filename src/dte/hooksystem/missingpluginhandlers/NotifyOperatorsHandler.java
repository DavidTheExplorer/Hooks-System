package dte.hooksystem.missingpluginhandlers;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.missingpluginhandlers.messager.MessagerHandler;
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
	
	private void notifyOperators(String... messages) 
	{
		OnlineOperators.get().forEach(operator -> operator.sendMessage(messages));
	}
}