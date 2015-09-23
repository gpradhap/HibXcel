package com.gs.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gs.common.command.FieldTypeCmd;

public class GenericsUtil {

	static Logger log = Logger.getLogger(GenericsUtil.class);
	
	//private ConcurrentHashMap<Integer, String> recursiveGenStrMap = new ConcurrentHashMap<Integer, String>(); 

	public static boolean isSimpleInstanceof(Class instanceClass) {
		return String.class == instanceClass
				|| Character.class == instanceClass
				|| Byte.class == instanceClass
				|| Short.class == instanceClass
				|| Integer.class == instanceClass
				|| Long.class == instanceClass
				|| Float.class == instanceClass 
				|| Double.class == instanceClass
				|| BigDecimal.class == instanceClass
				|| BigInteger.class == instanceClass
				|| Date.class == instanceClass
				|| Timestamp.class == instanceClass
				;
	}
	public static boolean isSimpleInstanceof(Object getMethodObj) {
		return getMethodObj instanceof String
				|| getMethodObj instanceof Integer
				|| getMethodObj instanceof Long
				|| getMethodObj instanceof Float
				|| getMethodObj instanceof Double
				|| getMethodObj instanceof Date
				|| getMethodObj instanceof java.sql.Timestamp
				;
	}
	
	public static boolean isCollectionInstance(Class instanceClass) {
		return List.class == instanceClass
				|| ArrayList.class == instanceClass
				|| Set.class == instanceClass
				|| HashSet.class == instanceClass
				|| SortedSet.class == instanceClass				
				|| Map.class == instanceClass
				|| HashMap.class == instanceClass				
				|| SortedMap.class == instanceClass				
				|| Collection.class == instanceClass
				;
	}	

	public static boolean isCollectionInstance(Object getMethodObj) {
		return getMethodObj instanceof List
				|| getMethodObj instanceof Set
				|| getMethodObj instanceof Map
				|| getMethodObj instanceof Collection
				;
	}
	
	
	public static boolean isComplexInstance(Class instanceClass) {
		return !(isSimpleInstanceof(instanceClass) || isCollectionInstance(instanceClass));
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
	
	public static Class getMethodParameterArgType(Method method) {
		
		if(null == method){return null;}
		
		Type[] genericParameterTypes = method
				.getGenericParameterTypes();
		
		Class parameterArgClass = null;
		
		try {
			for (Type genericParameterType : genericParameterTypes) {
				if (genericParameterType instanceof ParameterizedType) {
					if(log.isTraceEnabled()){
						log.trace("methodParameterArg is genericType"); }
					
					ParameterizedType aType = (ParameterizedType) genericParameterType;
					Type[] parameterArgTypes = aType
							.getActualTypeArguments();
					for (Type parameterArgType : parameterArgTypes) {
						parameterArgClass = (Class) parameterArgType;
					}
				} else {
					if(log.isTraceEnabled()){
						log.trace("methodParameterArg is NOT genericType"); }
					
					Type[] outputMthParamTypeArr = method
							.getParameterTypes();
					for (Type outputMthParamType : outputMthParamTypeArr) {
						parameterArgClass = (Class) outputMthParamType;
					}
				}
			}
		} catch (Exception e) {
			log.trace("methodParameterArg type could not be identified ?: "+e.getMessage());
		}

		if(log.isDebugEnabled()){
			log.debug("parameterArgClass = "+ parameterArgClass); }

		return parameterArgClass;
	}
	
	
	public static int genHashCode(Object sourceObj) {

		int genHashCode = -1;

		if(null == sourceObj){
			return genHashCode;
		}
		
		Class srcClass = sourceObj.getClass();
		
		log = Logger.getLogger(srcClass);

		// Method[] methods = srcClass.getDeclaredMethods();
		//Field[] inputFields = srcClass.getDeclaredFields();
		Field[] inputFields = GenericsUtil.getAllFields(srcClass);

		for(Field inputField : inputFields){

			String fieldName = inputField.getName();

			String methodName = FieldTypeCmd.createGetMethodName(inputField);

			if(StringUtils.isBlank(methodName)){continue;}
			
			try{
				Method srctMethod = srcClass.getMethod(methodName);

				Object srcMethodGetVal = srctMethod.invoke(sourceObj);

				if(null != srcMethodGetVal && isSimpleInstanceof(srcMethodGetVal)){
					if(log.isTraceEnabled()){
						log.trace("<GenericsUtil.genHashCode for>" + srcClass + "< methodName ?:>"
							+ methodName + "< methodValue ?:>" + srcMethodGetVal + "< hashCode?:>"
							+ genHashCode);
					}
					genHashCode += srcMethodGetVal.hashCode();
				}
			}catch(Exception e){
				log.trace("could not find hashCode for" + methodName + " getting exception "
						+ e.getMessage());
			}
		}
		if(log.isTraceEnabled()){
		log.trace("<FINAL GenericsUtil.genHashCode for>" + srcClass + "< hashCode?:>"
				+ genHashCode);}
		
		if(log.isDebugEnabled()){ log.debug("For Object ?:"+sourceObj+"< hashCode is >"+genHashCode);}
		return genHashCode;
	}
	
	public static int genHashCode(Class className, Object obj) {
		
		int genHashCode = -1;
		
		if(null == className || null == obj){return genHashCode;}
		log = Logger.getLogger(className);

		Method[] methods = className.getDeclaredMethods();
		for (int index = 0; index < methods.length; index++) {
			Method methodLo = methods[index];
			Object getMethodObj = null;
			if (null != methodLo
					&& (methodLo.getName().startsWith("get") || methodLo
							.getName().startsWith("is"))) {
				try {
					getMethodObj = methodLo.invoke(obj);
					if (null != getMethodObj && isSimpleInstanceof(getMethodObj)) {
						genHashCode +=getMethodObj.hashCode();
					}
				} catch (IllegalArgumentException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				}
			}
		}
		if(log.isDebugEnabled()){ log.debug("For Object ?:"+obj+"< hashCode is >"+genHashCode);}
		return genHashCode;
	}

	public static StringBuilder genToString(Object sourceObj) {
		
		if(null == sourceObj){return null;}
		Class srcClass = sourceObj.getClass();
		log = Logger.getLogger(srcClass);
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\n\t");
		Method[] methods = srcClass.getDeclaredMethods();
		
		//Field[] inputFields = srcClass.getDeclaredFields();
		Field[] inputFields = GenericsUtil.getAllFields(srcClass);

		for(Field inputField : inputFields){

			String fieldName = inputField.getName();

			String methodName = FieldTypeCmd.createGetMethodName(inputField);		
		
			if(StringUtils.isBlank(methodName)){continue;}
			
				try {

					Method srctMethod = srcClass.getMethod(methodName);

					Object srcMethodGetVal = srctMethod.invoke(sourceObj);
					
					if (null != srcMethodGetVal && isSimpleInstanceof(srcMethodGetVal)) {
						//if(!isSimpleInstanceof(srcMethodGetVal)){strBuilder.append("\n");}
						strBuilder.append(methodName);
						strBuilder.append(":");
						strBuilder.append(srcMethodGetVal.toString());
						strBuilder.append(", ");
					}else if (null == srcMethodGetVal) {
						strBuilder.append(methodName);
						strBuilder.append(":");
						strBuilder.append("null");
						strBuilder.append(", ");						
					}else if(!isSimpleInstanceof(srcMethodGetVal)){
						strBuilder.append(methodName);
						strBuilder.append(":");
						strBuilder.append("<--->");
						strBuilder.append(", ");								
					}
					
				} catch (IllegalArgumentException e) {
					log.trace("Exception invoke method "+ methodName + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.trace("Exception invoke method "+ methodName + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					log.trace("Exception invoke method "+ methodName + " msg:" + e.getMessage());
				}catch (NoSuchMethodException e) {
					log.trace("Exception invoke method "+ methodName + " msg:" + e.getMessage());
				}
			}
		
		//new GenericsUtil().recursiveGenStrMap.put(strBuilder.toString().hashCode(), strBuilder.toString());
		
		return strBuilder;
	}	
	
	public static StringBuilder genToString(Class className, Object obj) {
		
		if(null == className || null == obj){return null;}
		log = Logger.getLogger(className);
		
		StringBuilder strBuilder = new StringBuilder();
		
		Method[] methods = className.getDeclaredMethods();
		for (int index = 0; index < methods.length; index++) {
			Method methodLo = methods[index];
			Object getMethodObj = null;
			if (null != methodLo
					&& (methodLo.getName().startsWith("get") || methodLo
							.getName().startsWith("is"))) {
				try {
					getMethodObj = methodLo.invoke(obj);
					if (null != getMethodObj) {
						if(!isSimpleInstanceof(getMethodObj)){strBuilder.append("\n"); strBuilder.append("-------------------------------");}
						strBuilder.append(methodLo.getName());
						strBuilder.append(":");
						strBuilder.append(getMethodObj.toString());
						strBuilder.append(", ");
					}
				} catch (IllegalArgumentException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					log.trace("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				}
			}
		}
		return strBuilder;
	}

	public static Method[] getAllMethods(Object sourceObj) {
		
		if(null == sourceObj){return null;}
		
		Method[] privateMethods= sourceObj.getClass().getDeclaredMethods();
		Method[] publicMethods= sourceObj.getClass().getMethods();
		
		for(Method prvMethod : privateMethods){
			if(log.isTraceEnabled()){ log.trace(" prvMethod : " + prvMethod.getName()); }
		}
		for(Method pubMethod:publicMethods){if(log.isTraceEnabled()){log.trace(" publicMethods : "+pubMethod.getName());}}
		
		ArrayList<Method> tempArray = new ArrayList<Method>();
		if(null != privateMethods && privateMethods.length > 0){
			tempArray.addAll(Arrays.asList(privateMethods));
		}
		if(null != publicMethods && publicMethods.length > 0){
			tempArray.addAll(Arrays.asList(publicMethods));
		}
		Method [] srcMethods = tempArray.toArray(new Method[tempArray.size()]);
		return srcMethods;
	}
	
	public static Method[] getAllMethods(Class sourceClazz) {
		
		if(null == sourceClazz){return null;}
		
		Method[] privateMethods= sourceClazz.getDeclaredMethods();
		Method[] publicMethods= sourceClazz.getMethods();
		
		for(Method prvMethod : privateMethods){
			if(log.isTraceEnabled()){ log.trace(" prvMethod : " + prvMethod.getName()); }
		}
		for(Method pubMethod:publicMethods){if(log.isTraceEnabled()){log.trace(" publicMethods : "+pubMethod.getName());}}
		
		ArrayList<Method> tempArray = new ArrayList<Method>();
		if(null != privateMethods && privateMethods.length > 0){
			tempArray.addAll(Arrays.asList(privateMethods));
		}
		if(null != publicMethods && publicMethods.length > 0){
			tempArray.addAll(Arrays.asList(publicMethods));
		}
		Method [] srcMethods = tempArray.toArray(new Method[tempArray.size()]);
		return srcMethods;
	}	
	
	public static Field[] getAllFields(Object sourceObj) {
		
		if(null == sourceObj){return null;}
		
		Field[] privateFields= sourceObj.getClass().getDeclaredFields();
		Field[] publicFields= sourceObj.getClass().getFields();
		
		for(Field prvField : privateFields){
			if(log.isTraceEnabled()){ log.trace(" prvField : " + prvField.getName()); }
		}
		for(Field pubField:publicFields){if(log.isTraceEnabled()){log.trace(" publicFields : "+pubField.getName());}}
		
		ArrayList<Field> tempArray = new ArrayList<Field>();
		if(null != privateFields && privateFields.length > 0){
			tempArray.addAll(Arrays.asList(privateFields));
		}
		if(null != publicFields && publicFields.length > 0){
			tempArray.addAll(Arrays.asList(publicFields));
		}
		Field [] srcFields = tempArray.toArray(new Field[tempArray.size()]);
		return srcFields;
	}	
	
	
	public static Field[] getAllFields(Class sourceClazz) {
		
		if(null == sourceClazz){return null;}
		
		Field[] privateFields= sourceClazz.getDeclaredFields();
		Field[] publicFields= sourceClazz.getFields();
		
		for(Field prvField:privateFields){if(log.isTraceEnabled()){log.trace(" prvField : "+prvField.getName());}}
		for(Field pubField:publicFields){if(log.isTraceEnabled()){log.trace(" publicFields : "+pubField.getName());}}
		
		ArrayList<Field> tempArray = new ArrayList<Field>();
		if(null != privateFields && privateFields.length > 0){
			tempArray.addAll(Arrays.asList(privateFields));
		}
		if(null != publicFields && publicFields.length > 0){
			tempArray.addAll(Arrays.asList(publicFields));
		}
		Field [] srcFields = tempArray.toArray(new Field[tempArray.size()]);
		return srcFields;
	}	

	public static Field getMatchField(Object inParamObj, String matchFieldName) {

		if(null == inParamObj || StringUtils.isBlank(matchFieldName)){return null;}
		
		Field[] inFields = GenericsUtil.getAllFields(inParamObj);

		Field matchField = null;
		for(Field inFieldLo : inFields){

			if(null == inFieldLo){
				continue;
			}

			String destFieldName = inFieldLo.getName();

			if(matchFieldName.equals(destFieldName)){
				matchField = inFieldLo;
				break;
			}
		}
		return matchField;
	}
	

	public static Object invokeGetMethod(Object inParamObj, String fieldName) {
		
		if(null == inParamObj || StringUtils.isBlank(fieldName)){return null;}
		
		String matchMethodName = FieldTypeCmd.createGetMethodName(fieldName);
		
		Method[] inObjMethods = GenericsUtil.getAllMethods(inParamObj);
		
		Method matchMethod = null;

		for(Method inMethod : inObjMethods){
			if(null == inMethod){
				continue;
			}

			String methodName = inMethod.getName();

			if(matchMethodName.equalsIgnoreCase(methodName)){
				matchMethod = inMethod;
				break;
			}
		}
		
		if(null == matchMethod){return null;}
		
		Object srcGetMethodVal = null;
		try{

			srcGetMethodVal = matchMethod.invoke(inParamObj);

		}catch(Exception e){
			log.error("Exception invoke method " + matchMethod.getName() + " msg:"
					+ e.getMessage());
		}
		return srcGetMethodVal;
	}

	
	public static Method getMatchMethod(Object inParamObj, String matchMethodName) {
		
		if(null == inParamObj || StringUtils.isBlank(matchMethodName)){return null;}
		
		Method[] inObjMethods = GenericsUtil.getAllMethods(inParamObj);
		
		Method matchMethod = null;

		for(Method inMethod : inObjMethods){
			if(null == inMethod){
				continue;
			}

			String methodName = inMethod.getName();

			if(matchMethodName.equalsIgnoreCase(methodName)){
				matchMethod = inMethod;
				break;
			}
		}
		return matchMethod;
	}
	
	public static Object invokeGetMethod(Object srcObject, Method inMethod){
		
		if(null == srcObject || null == inMethod){return null;}
		
		Object srcGetMethodVal = null;
		try{

			srcGetMethodVal = inMethod.invoke(srcObject);

		}catch(Exception e){
			log.error("Exception invoke method " + inMethod.getName() + " msg:"
					+ e.getMessage());
		}
		return srcGetMethodVal;
	}
	
	/***
	 * 
	 * @param objInput
	 * @return Boolean (returns true: if empty object passed as input;)
	 */
	public static Boolean isEmptySimplIns(Object objInput) {
		if(null == objInput){
			return true;
		}
		/*Method[] methods = GenericsUtil.getAllMethods(objInput);

		try{
			for(Method methodLo : methods){
				if(null == methodLo){
					continue;
				}

				if(null != GenericsUtil.invokeGetMethod(objInput, methodLo)){
					return false;
				}
			}
		}catch(Exception e){
			log.error("Exception: isEmptySimplIns fails");
		}*/
		
		Field[] fields = GenericsUtil.getAllFields(objInput);

		try{
			for(Field fieldLo : fields){
				if(null == fieldLo){
					continue;
				}

				if(null != GenericsUtil.invokeGetMethod(objInput, fieldLo.getName())){
					return false;
				}
			}
		}catch(Exception e){
			log.error("Exception: isEmptySimplIns fails");
		}
		return true;
	}
}

