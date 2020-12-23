package dte.hooksystem.plugins.absencehandlers.list.logging;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.absencehandlers.PluginAbsenceHandler;
import dte.hooksystem.plugins.absencehandlers.copyable.CopyableHandler;

public abstract class MessagerHandler implements PluginAbsenceHandler, CopyableHandler<MessagerHandler>
{
	private final List<String> templateMessages;

	public MessagerHandler(String[] messages) 
	{
		this.templateMessages = Lists.newArrayList(messages);
	}
	
	@Override
	public void handle(PluginHook failedHook) 
	{
		String[] finalMessages = createMessagesFor(failedHook);
		
		Arrays.stream(finalMessages).forEach(this::sendMessage);
	}
	protected String[] createMessagesFor(PluginHook failedHook) 
	{
		return this.templateMessages.stream()
				.map(message -> injectHookInfo(message, failedHook))
				.toArray(String[]::new);
	}
	public abstract void sendMessage(String message);
	
	
	/*
	 * Copy Utilities
	 */
	protected String[] getCopiedMessages() 
	{
		return this.templateMessages.toArray(new String[this.templateMessages.size()]);
	}
	
	
	private String injectHookInfo(String text, PluginHook failedHook) 
	{
		return text.replace("%plugin", failedHook.getPluginName());
	}
}