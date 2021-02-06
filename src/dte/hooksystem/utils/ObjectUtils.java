package dte.hooksystem.utils;

import java.util.function.Consumer;

public class ObjectUtils
{
	//Container of static methods
	private ObjectUtils(){}
	
	public static <T> T getOrDefault(T object, T defaultValue) 
	{
		return object != null ? object : defaultValue;
	}
	public static <T> void ifNotNull(T object, Consumer<T> action) 
	{
		if(object != null)
			action.accept(object);
	}
}