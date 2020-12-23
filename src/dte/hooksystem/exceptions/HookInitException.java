package dte.hooksystem.exceptions;

/**
 * Thrown when any Exception was thrown during the <i>initialization</i> of an Hook, so the developer can handle it more appropriately.
 */
public class HookInitException extends HookException
{
	private final Exception initException;
	
	private static final long serialVersionUID = 4863066975268384377L;

	public HookInitException(String pluginName, Exception initException)
	{
		super(pluginName, "%plugin's Registered Hook generated an Exception during its Initalization: " + initException);
		
		this.initException = initException;
	}
	public Exception getInitException() 
	{
		return this.initException;
	}
}