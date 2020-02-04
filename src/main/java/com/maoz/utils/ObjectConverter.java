package com.maoz.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.maoz.utils.annotations.DateValueFormat;
import com.maoz.utils.annotations.StringDelimeterToList;
/**
 * @author  Pongpat Phokeed
 * @version 1.0
 * @since   2019/10/17
 */
public class ObjectConverter {

	private final static String THREAD = Thread.currentThread().getId() + " " + Thread.currentThread().getName();

	public ObjectConverter() {

	}

	public <T> T convertValue(Object source, Class<T> target) {
		T newObj = null;
		try {
			newObj = target.getDeclaredConstructor().newInstance();

			Field[] fields = newObj.getClass().getDeclaredFields();

			for (Field f : fields) {

				System.out.println("["+new Date()+"] ["+THREAD+"] tartget Field name" + f.getName());
				System.out.println("["+new Date()+"] ["+THREAD+"] tartget Field type" + f.getType());

				if(!"serialVersionUID".equals(f.getName())) {
					for (Method m : source.getClass().getMethods()) {
						if (m.getName().startsWith("get") && m.getName().length() == f.getName().length() + 3) {
							if (m.getName().toLowerCase().endsWith(f.getName().toLowerCase())) {
								//setValue
								f.setAccessible(true);
								try {
									Object value = m.invoke(source);
									//valueFormat anno
									System.out.println("["+new Date()+"] ["+THREAD+"] target field Type " + f.getType().getName());
									System.out.println("["+new Date()+"] ["+THREAD+"] source field Type " + m.getReturnType().getName());
									DateValueFormat valueFormat = m.getAnnotation(DateValueFormat.class);
									StringDelimeterToList transFormTo = m.getAnnotation(StringDelimeterToList.class);
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
										
										
										
									} else if(null != transFormTo) {
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

									} else {
										value = this.transformValue(m.getReturnType(), f.getType(), value);
									}

									f.set(newObj, value);
									System.out.println("["+new Date()+"] ["+THREAD+"] Value " + value);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					}
				}
				

			}

		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newObj;
	}

	private Object transformValue(Class sourceFieldType, Class targetFieldType, Object sourceValue){
		Object result = null;
		if(sourceFieldType == targetFieldType){
			result = sourceValue;
		} else {

			if (null != sourceValue) {
				if(sourceFieldType == String.class) {
					String xValue = (String) sourceValue;

					if(targetFieldType == Double.class) {
						result = Double.parseDouble(xValue);
					}
					if(targetFieldType == Integer.class) {
						result = Integer.parseInt(xValue);
					}
					if(targetFieldType == BigDecimal.class) {
						result = new BigDecimal(xValue);
					}
				} else if(sourceFieldType == Double.class) {
					Double xValue = (Double) sourceValue;

					if(targetFieldType == Integer.class) {
						result = Integer.parseInt(Double.toString(xValue));
					}
					if(targetFieldType == BigDecimal.class) {
						result = new BigDecimal(xValue);
					}
					if(targetFieldType == String.class) {
						result = xValue.toString();
					}
				} else if(sourceFieldType == Integer.class) {
					Integer xValue = (Integer) sourceValue;

					if(targetFieldType == Double.class) {
						result = Double.parseDouble(xValue.toString());
					}
					if(targetFieldType == BigDecimal.class) {
						result = new BigDecimal(xValue);
					}
					if(targetFieldType == String.class) {
						result = xValue.toString();
					}
				} else {
					result = sourceValue;
				}
			}
		}
		return result;
	}

}
