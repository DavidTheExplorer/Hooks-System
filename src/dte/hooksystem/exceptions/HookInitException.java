package dte.hooksystem.exceptions;

import dte.hooksystem.hook.PluginHook;

/**
 * Thrown when an Exception was thrown during the <i>initialization</i> of a hook, so it can be handled appropriately.
 */
public class HookInitException extends HookingException
{
	private final Exception initException;

	private static final long serialVersionUID = 4863066975268384377L;
	
	public HookInitException(PluginHook hook, Exception initException)
	{
		super(hook, "%plugin%'s Registered Hook generated an Exception during initalization!");
		
		initCause(this.initException = initException);
	}
	public Exception getException() 
	{
		return this.initException;
	}
}