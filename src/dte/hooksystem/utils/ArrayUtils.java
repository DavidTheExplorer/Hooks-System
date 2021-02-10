package dte.hooksystem.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

public class ArrayUtils
{
	//Container of static methods
	private ArrayUtils(){}
	
	public static <T> Iterable<T> toIterable(T[] array)
	{
		return Lists.newArrayList(array);
	}
	public static <T, C extends Collection<T>> C toCollection(T[] array, Supplier<C> baseSupplier)
	{
		C collection = baseSupplier.get();
		collection.addAll(Arrays.asList(array));
		
		return collection;
	}
}