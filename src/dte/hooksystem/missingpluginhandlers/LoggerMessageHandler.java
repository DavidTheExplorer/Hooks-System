package dte.hooksystem.plugins.missinghandlers.list.messagers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerMessageHandler extends MessagerHandler
{
	private final Logger logger;
	private final Level logLevel;
	
	public LoggerMessageHandler(Logger logger, Level logLevel)
	{
		this.logger = logger;
		this.logLevel = logLevel;
	}
	
	@Override
	public void sendMessage(String message) 
	{
		this.logger.log(this.logLevel, message);
	}
}