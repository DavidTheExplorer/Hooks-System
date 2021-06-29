package dte.hooksystem.missingpluginhandlers;

import dte.hooksystem.missingpluginhandlers.messager.MessagerHandler;
import dte.hooksystem.utils.OnlineOperators;

public class NotifyOperatorsHandler extends MessagerHandler
{
	@Override
	protected void sendMessage(String message) 
	{
		OnlineOperators.get().forEach(operator -> operator.sendMessage(message));
	}
}