package myJavaClasses;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class ClassReader
{
	public static ArrayList<Field> returnPrimitiveFieldsOnly(Object o)
	{
		Class<?> clazz = o.getClass();
		ArrayList<Field> fields_to_return = new ArrayList<Field>() ;
		
		// debugg
		for (Field f : clazz.getDeclaredFields())
		{
			if (f.getClass().isPrimitive())
			{
				fields_to_return.add(f);
			}
		}
		
		return fields_to_return ;
	}
	

	public static ArrayList<Field> returnFields(Object o, boolean exclude_static)
	{
		Class<?> clazz = o.getClass();
		ArrayList<Field> fields_to_return = new ArrayList<Field>() ;
		
		// debugg
		for (Field f : clazz.getDeclaredFields())
		{
			if (exclude_static && !java.lang.reflect.Modifier.isStatic(f.getModifiers()))
			// ONLY if boolean == true : add if f is NOT static
			{
				fields_to_return.add(f);
			}
		}
		
		return fields_to_return ;
	}
	
	
	
	public static Object returnFieldValFromName(Object o, String field_name) 
	{
		Class<?> c = o.getClass();

		try
		{
			Field f = c.getDeclaredField(field_name);
			f.setAccessible(true);

			Object val = f.get(o);
			
			return val ;
		}
		
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null ;
		}
				
	}
	

	public static void displayFieldsAndValues(ArrayList<?> al)
	{
		for (Object o : al)
		{
			ArrayList<Field> fields = ClassReader.returnFields(o, true);
			
			for (Field f : fields)
			{
				System.out.println(f.getName() + " : " + 
						ClassReader.returnFieldValFromName(o, f.getName()).toString()
					) ;
			}
			
			Disp.line();
		}
	}
	
}
