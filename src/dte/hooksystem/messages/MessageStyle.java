package dte.hooksystem.messages;

import static dte.hooksystem.utils.ObjectUtils.ifNotNull;

import java.util.ArrayList;	
import java.util.List;
import java.util.function.UnaryOperator;

import com.google.common.collect.Streams;

public class MessageStyle
{
	private String prefix, suffix;

	//transforming actions that are applied on the final message
	private final List<UnaryOperator<String>> finalTouches = new ArrayList<>();

	//the final template
	private String template;

	//this instance always returns the given raw message
	public static final MessageStyle RAW = new MessageStyle();
	
	public MessageStyle prefixedWith(String prefix) 
	{
		this.prefix = prefix;
		updateTemplate();
		
		return this;
	}
	public MessageStyle suffixedWith(String suffix) 
	{
		this.suffix = suffix;
		updateTemplate();
		
		return this;
	}
	public MessageStyle withFinalTouch(UnaryOperator<String> finalTouch) 
	{
		this.finalTouches.add(finalTouch);
		return this;
	}
	public String apply(String message) 
	{
		if(this == RAW)
			return message;

		//init the message template if needed
		if(this.template == null)
			updateTemplate();
		
		String messageInjected = String.format(this.template, message);
		
		return applyFinalTouches(messageInjected);
	}
	public String[] apply(Iterable<String> rawMessages)
	{
		return Streams.stream(rawMessages)
				.map(this::apply)
				.toArray(String[]::new);
	}
	public MessageStyle copy() 
	{
		MessageStyle copy = new MessageStyle();

		ifNotNull(this.prefix, copy::prefixedWith);
		ifNotNull(this.suffix, copy::suffixedWith);
		this.finalTouches.forEach(copy::withFinalTouch);

		return copy;
	}

	private void updateTemplate() 
	{
		StringBuilder builder = new StringBuilder();
		
		ifNotNull(this.prefix, prefix -> builder.append(prefix).append(" ")); //prefix
		builder.append("%s"); //the message placeholder
		ifNotNull(this.suffix, suffix -> builder.append(" ").append(suffix)); //suffix
		
		this.template = builder.toString();
	}
	private String applyFinalTouches(String text) 
	{
		for(UnaryOperator<String> touch : this.finalTouches) 
			text = touch.apply(text);
		
		return text;
	}
}