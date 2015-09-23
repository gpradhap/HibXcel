package com.gs.common.util;


public class FactoryHelper {

	private static String IMPL = "Impl";
	
	public static <T>T getImplInstance(Class<T> className){
		if(null == className) return null;
		return getInstance(className.getName()+IMPL);
	}
	
	public static <T>T getInstance(String classname) {

		ClassLoader classLoader = getClassLoader();
		try {
			if (null == classLoader) {
				return null;
			}
			return (T)classLoader.loadClass(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T>T getInstance(Class<T> classname) {

		ClassLoader classLoader = getClassLoader();
		try {
			if (null == classLoader) {
				return null;
			}
			return (T)classLoader.loadClass(classname.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ClassLoader getClassLoader() {
		// Try with the Thread Context Loader.
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		if (null == classLoader) {
			// get the classloader that loaded this class.
			classLoader = ClassLoader.class.getClassLoader();
		}
		if (null == classLoader) {
			classLoader = ClassLoader.getSystemClassLoader();
		}
		return classLoader;
	}
}
