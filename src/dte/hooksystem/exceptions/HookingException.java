package dte.hooksystem.exceptions;

import dte.hooksystem.hook.PluginHook;

/**
 * Thrown to indicate a problem during the hook process to a certain plugin on the server.
 */
public class HookingException extends RuntimeException
{
	private final PluginHook hook;
	
	private static final long serialVersionUID = -7835173766962305604L;
	
	public HookingException(PluginHook hook, String message) 
	{
		super(message.replace("%plugin%", hook.getPluginName()));
		
		this.hook = hook;
	}
	
	public PluginHook getHook() 
	{
		return this.hook;
	}
}