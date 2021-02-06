package dte.hooksystem.utils;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FunctionUtils
{
	//Container of static methods
	private FunctionUtils(){}
	
	public static <T> UnaryOperator<T> toUnaryOperator(Function<T, T> function)
	{
		return function::apply;
	}
}