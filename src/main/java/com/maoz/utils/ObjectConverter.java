package com.maoz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.maoz.utils.annotations.DateValueFormat;
import com.maoz.utils.annotations.StringDelimeterToList;

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

				System.out.println("["+new Date()+"] ["+THREAD+"] tartget Field name" + f.getName());
				System.out.println("["+new Date()+"] ["+THREAD+"] tartget Field type" + f.getType());

				for (Method m : source.getClass().getMethods()) {
					if (m.getName().startsWith("get") && m.getName().length() == f.getName().length() + 3) {
						if (m.getName().toLowerCase().endsWith(f.getName().toLowerCase())) {
							//setValue
							f.setAccessible(true);
							try {
								Object value = m.invoke(source);
								//valueFormat anno
								DateValueFormat valueFormat = m.getAnnotation(DateValueFormat.class);
								if(null != valueFormat) {
									
									String format = valueFormat.format();
									
									System.out.println("["+new Date()+"] ["+THREAD+"] DateValueFormat " + format);
									
									SimpleDateFormat sdf = new SimpleDateFormat(format);
									String valueString = String.valueOf(value);
									try {
										Date dateObject = sdf.parse(valueString);
										value = dateObject;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									
								} 
								
								StringDelimeterToList transFormTo = m.getAnnotation(StringDelimeterToList.class);
								if(null != transFormTo) {
									String sourceType = transFormTo.sourceType();
									String targetType = transFormTo.targetType();
									String delimeter = transFormTo.delimeter();
									
									System.out.println("["+new Date()+"] ["+THREAD+"] TransFormTo source " + sourceType);
									System.out.println("["+new Date()+"] ["+THREAD+"] TransFormTo target " + targetType);
									System.out.println("["+new Date()+"] ["+THREAD+"] TransFormTo delimeter " + delimeter);
									
									if(StringUtils.equals("java.lang.String", sourceType) && StringUtils.equals("java.util.List", targetType)) {
										List<String> valueList = new ArrayList<String>();
										String strValue = String.valueOf(value);
										String[] splitedString = StringUtils.split(strValue, delimeter);
										for (int i = 0; i < splitedString.length; i++) {
											String s = splitedString[i];
											valueList.add(s);
										}
										value = valueList;
									}
									
								}
								
								
								f.set(newObj, value);
								System.out.println("["+new Date()+"] ["+THREAD+"] Value " + value);
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
