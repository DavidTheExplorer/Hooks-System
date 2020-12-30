package dte.hooksystem.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class Iterators 
{
	//Container of static methods
	private Iterators(){}

	public static <T> void loop(T[] array, Consumer<T> action, Consumer<T> untilLastIteration) 
	{
		loop(Arrays.asList(array), action, untilLastIteration);
	}
	public static <T> void loop(Iterable<T> iterable, Consumer<T> action, Consumer<T> untilLastIteration)
	{
		for(Iterator<T> iterator = iterable.iterator(); iterator.hasNext();) 
		{
			T element = iterator.next();

			action.accept(element);

			if(iterator.hasNext())
				untilLastIteration.accept(element);
		}
	}
}
