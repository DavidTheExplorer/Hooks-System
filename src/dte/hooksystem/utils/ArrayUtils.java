package dte.hooksystem.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public class ArrayUtils
{
	//Container of static methods
	private ArrayUtils(){}
	
	public static <T, C extends Collection<T>> C toCollection(T[] array, Supplier<C> baseSupplier)
	{
		C collection = baseSupplier.get();
		
		collection.addAll(Arrays.asList(array));
		
		return collection;
	}
}