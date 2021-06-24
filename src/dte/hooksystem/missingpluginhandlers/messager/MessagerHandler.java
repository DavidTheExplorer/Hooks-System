package dte.hooksystem.plugins.missinghandlers.list.messagers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dte.hooksystem.hooks.PluginHook;
import dte.hooksystem.plugins.missinghandlers.MissingPluginHandler;

public abstract class MessagerHandler implements MissingPluginHandler
{
	private final List<String> templateMessages = new ArrayList<>();
	
	public void addMessages(String... message) 
	{
		this.templateMessages.addAll(Arrays.asList(message));
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
	
	
	private String injectHookInfo(String text, PluginHook failedHook) 
	{
		return text.replace("%plugin", failedHook.getPluginName());
	}
}