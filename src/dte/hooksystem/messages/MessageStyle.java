package dte.hooksystem.messages;

import static dte.hooksystem.utilities.ObjectsUtilities.ifNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class MessageStyle
{
	private String prefix, suffix;

	//transforming actions that are applied on the final message
	private final List<UnaryOperator<String>> finalTouches = new ArrayList<>();

	//the final template
	private String template;

	//this instance always returns the given raw message
	public static final MessageStyle RAW = new MessageStyle();

	public static MessageStyle copyOf(MessageStyle style) 
	{
		MessageStyle copy = new MessageStyle();

		ifNotNull(style.prefix, copy::prefixedWith);
		ifNotNull(style.suffix, copy::suffixedWith);
		ifNotNull(style.template, styleTemplate -> copy.template = styleTemplate);
		style.finalTouches.forEach(copy::withFinalTouch);

		return copy;
	}
	public MessageStyle prefixedWith(String prefix) 
	{
		this.prefix = prefix;
		return this;
	}
	public MessageStyle suffixedWith(String suffix) 
	{
		this.suffix = suffix;
		return this;
	}
	public MessageStyle withFinalTouch(UnaryOperator<String> finalTouch) 
	{
		this.finalTouches.add(finalTouch);
		return this;
	}
	public String apply(String rawMessage) 
	{
		if(this == RAW)
			return rawMessage;

		//init the message template if needed
		if(this.template == null)
			this.template = createTemplate();

		String messageInjected = String.format(this.template, rawMessage);

		return addFinalTouches(messageInjected);
	}
	public String[] apply(String[] rawMessages) 
	{
		return Arrays.stream(rawMessages)
				.map(this::apply)
				.toArray(String[]::new);
	}
	List<UnaryOperator<String>> getFinalTouches()
	{
		return this.finalTouches;
	}


	private String createTemplate() 
	{
		StringBuilder template = new StringBuilder();

		//prefix
		ifNotNull(this.prefix, prefix -> template.append(prefix).append(" "));

		//the message placeholder
		template.append("%s"); 

		//suffix
		ifNotNull(this.suffix, suffix -> template.append(" ").append(suffix));

		return template.toString();
	}
	private String addFinalTouches(String text) 
	{
		for(UnaryOperator<String> touch : this.finalTouches) 
		{
			text = touch.apply(text);
		}
		return text;
	}
}