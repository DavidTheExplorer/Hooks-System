package dte.hooksystem.exceptions;

/**
 * Thrown to indicate a problem during the hook process to a certain plugin on the server.
 */
public class HookException extends RuntimeException
{
	private final String pluginName;
	
	private static final long serialVersionUID = -7835173766962305604L;
	
	public HookException(String pluginName, String message) 
	{
		super(message.replace("%plugin", pluginName));
		
		this.pluginName = pluginName;
	}
	public String getPluginName() 
	{
		return this.pluginName;
	}
}