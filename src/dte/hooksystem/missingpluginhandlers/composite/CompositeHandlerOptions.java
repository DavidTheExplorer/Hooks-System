package dte.hooksystem.missingpluginhandlers.composite;

public class CompositeHandlerOptions 
{
	private boolean orderedByFIFO = false;
	
	public static final CompositeHandlerOptions FIFO = new CompositeHandlerOptions()
			.useFIFO();
	
	public CompositeHandlerOptions useFIFO() 
	{
		this.orderedByFIFO = true;
		return this;
	}
	public boolean usesFIFO()
	{
		return this.orderedByFIFO;
	}
}