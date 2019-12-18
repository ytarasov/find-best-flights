package com.projector.edu.findflight.config.properties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ConfigPropertiesReader {

	public Object readPropertiesFile(String propertiesFileName, Object propertiesHolder) {
		MessageSource messageSource = getMessage(propertiesFileName);
		try {
			Class<? extends Object> clazz = propertiesHolder.getClass();
			Field[] fields = clazz.getDeclaredFields();
			Method[] methods = clazz.getDeclaredMethods();
			Map<String, Method> methodMap = Arrays.asList(methods).stream().collect(Collectors.toMap(method -> method.getName(), method -> method));			
			for (Field field : fields) {
				Annotation[] annotations = field.getDeclaredAnnotations();
				FileProperty filePropertyAnnotation = (FileProperty) getFileProperty(annotations);				
				if (filePropertyAnnotation == null) {
					continue;
				}
				String fieldName = field.getName();
				Class<?> fieldType = field.getType();
				String filePropertyName = filePropertyAnnotation.propertyName();
				Object propertyValue = getPropertyValue(messageSource, filePropertyName, fieldType);
				Method method = methodMap.get(getMethodName(fieldName, "set"));
				method.invoke(propertiesHolder, propertyValue);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return propertiesHolder;
	}
	
	public Object mergeConfigurations(Object configuration1, Object configuration2) {
		try {
			Method[] methods1 = configuration1.getClass().getDeclaredMethods();
			Method[] methods2 = configuration2.getClass().getDeclaredMethods();
			Map<String, Method> methodMap1 = Arrays.asList(methods1).stream().collect(Collectors.toMap(method -> method.getName(), method -> method));
			Map<String, Method> methodMap2 = Arrays.asList(methods2).stream().collect(Collectors.toMap(method -> method.getName(), method -> method));
			
			Field[] fields = configuration1.getClass().getDeclaredFields();
			for (Field field : fields) {
				Annotation[] annotations = field.getDeclaredAnnotations();
				WebRequestProperty webRequestPropertyAnnotation = (WebRequestProperty) getWebRequestProperty(annotations);				
				if (webRequestPropertyAnnotation == null) {
					continue;
				}
				String fieldName = field.getName();
				Object fieldType = field.getType();
				String prefix = "";
				if (fieldType == boolean.class) {
					prefix = "is";
				} else {
					prefix = "get";
				}
				Method getterMethod2 = methodMap2.get(getMethodName(fieldName, prefix));
				Method setterMethod1 = methodMap1.get(getMethodName(fieldName, "set"));
				Object value = getterMethod2.invoke(configuration2);
				setterMethod1.invoke(configuration1, value);
			}			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return configuration1;
	}
	
	private FileProperty getFileProperty(Annotation[] annotations) {
		for(Annotation annotation : annotations) {
			if (annotation instanceof FileProperty) {
				return (FileProperty) annotation;
			}
		}
		return null;
	}
	
	private WebRequestProperty getWebRequestProperty(Annotation[] annotations) {
		for(Annotation annotation : annotations) {
			if (annotation instanceof WebRequestProperty) {
				return (WebRequestProperty) annotation;  
			}
		}
		return null;
	}
	
	private Object getPropertyValue(MessageSource messageSource, String propertyName, Class<?> propertyType) {
		Object propertyValue = null;
		if (propertyType.equals(int.class)) {
			propertyValue = getIntegerPropertyValue(messageSource, propertyName);
			return propertyValue;
		}
		if (propertyType.equals(double.class)) {
			propertyValue = getDoublePropertyValue(messageSource, propertyName);
			return propertyValue;
		}
		if (propertyType.equals(boolean.class)) {
			propertyValue = getBooleanPropertyValue(messageSource, propertyName);
			return propertyValue;
		}
		return propertyValue;
	}	

	private String getMethodName(String fieldName, String prefix) {
		String firstChar = fieldName.substring(0,1).toUpperCase();
		String fixedFieldName = firstChar + fieldName.substring(1);
		return prefix + fixedFieldName;
	}
	
	private int getIntegerPropertyValue(MessageSource messageSource, String propertyName) {
		String propertyObjectValue = messageSource.getMessage(propertyName, null, null);
		return Integer.parseInt(propertyObjectValue);
	}
	
	private Object getDoublePropertyValue(MessageSource messageSource, String propertyName) {
		String propertyObjectValue = messageSource.getMessage(propertyName, null, null);
		return Double.parseDouble(propertyObjectValue);
	}
	
	private Object getBooleanPropertyValue(MessageSource messageSource,	String propertyName) {		
		String propertyObjectValue = messageSource.getMessage(propertyName, null, null);
		return Boolean.parseBoolean(propertyObjectValue);
	}
	
	private MessageSource getMessage(String propertyFileName) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasename("classpath:" + propertyFileName);
		messageSource.setCacheSeconds(3);
		return messageSource;
	}
	
}
