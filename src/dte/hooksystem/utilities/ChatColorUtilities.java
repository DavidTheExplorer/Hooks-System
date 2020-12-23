package dte.hooksystem.utilities;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.ChatColor;

//TODO: make it possible to escape colors with a slash before them
public class ChatColorUtilities 
{
	//Container of static methods
	private ChatColorUtilities(){}
	
	private static final Map<String, ChatColor> COLOR_BY_NAME = Arrays.stream(ChatColor.values())
			.collect(toMap(ChatColor::name, Function.identity()));
	
	public static String colorizeLiterals(String text) 
	{
		for(Map.Entry<String, ChatColor> entry : COLOR_BY_NAME.entrySet()) 
		{
			String colorIdentifier = entry.getKey() + "+";
			ChatColor color = entry.getValue();
			
			text = text.replace(colorIdentifier, color.toString());
		}
		return text;
	}
}
