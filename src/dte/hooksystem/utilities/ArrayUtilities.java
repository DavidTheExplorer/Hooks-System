package dte.hooksystem.utilities;

public class ArrayUtilities
{
	//Container of static methods
	private ArrayUtilities(){}

	@SuppressWarnings("unchecked")
	public static <T> T[] addTo(T[] array, T... elements)
	{
		return merge(array, elements);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] merge(T[] array1, T[] array2) 
	{
		T[] result = (T[]) new Object[array1.length + array2.length];
		
		//insert the first array's elements
		for(int i = 0; i < array1.length; i++)
		{
			result[i] = array1[i];
		}
		
		//insert the second array's elements
		for(int i = 0; i < array2.length; i++) 
		{
			result[array1.length + i] = array2[i];
		}
		return result;
	}
}
