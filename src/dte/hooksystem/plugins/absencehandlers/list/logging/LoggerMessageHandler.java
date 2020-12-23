package dte.hooksystem.plugins.absencehandlers.list.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerMessageHandler extends MessagerHandler
{
	private final Logger logger;
	private final Level logLevel;
	
	public LoggerMessageHandler(Logger logger, Level logLevel, String[] messages)
	{
		super(messages);
		
		this.logger = logger;
		this.logLevel = logLevel;
	}
	
	@Override
	public void sendMessage(String message) 
	{
		this.logger.log(this.logLevel, message);
	}

	@Override
	public MessagerHandler copy() 
	{
		return new LoggerMessageHandler(this.logger, this.logLevel, getCopiedMessages());
	}
}
