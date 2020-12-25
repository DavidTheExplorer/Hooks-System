package dte.hooksystem.utilities;

import org.bukkit.ChatColor;

//TODO: allow to escape colors with a slash before them
public class ChatColorUtilities 
{
	//Container of static methods
	private ChatColorUtilities(){}
	
	public static String colorizeLiterals(String text) 
	{
		for(ChatColor color : ChatColor.values()) 
		{
			text = text.replace(identifierOf(color), color.toString());
		}
		return text;
	}
	private static String identifierOf(ChatColor color) 
	{
		return color.name() + "+";
	}
}