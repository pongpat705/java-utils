package com.maoz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectConverter {

	private final static String THREAD = Thread.currentThread().getId() + " " + Thread.currentThread().getName();

	public ObjectConverter() {

	}

	public <T> T convertValue(Object source, Class<T> target) {
		T newObj = null;
		try {
			newObj = target.newInstance();

			Field[] fields = newObj.getClass().getDeclaredFields();

			for (Field f : fields) {

				System.out.println(THREAD + " Field " + f.getName());

				for (Method m : source.getClass().getMethods()) {
					if (m.getName().startsWith("get") && m.getName().length() == f.getName().length() + 3) {
						if (m.getName().toLowerCase().endsWith(f.getName().toLowerCase())) {
							f.setAccessible(true);
							try {
								f.set(newObj, m.invoke(source));
								System.out.println(THREAD + " Value " + m.invoke(source));
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}

			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newObj;
	}

}
