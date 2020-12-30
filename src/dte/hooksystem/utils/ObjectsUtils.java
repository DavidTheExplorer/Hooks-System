package dte.hooksystem.utils;

import java.util.function.Consumer;

public class ObjectsUtils
{
	//Container of static methods
	private ObjectsUtils(){}
	
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