package com.gs.common.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.command.FieldTypeCmd;
import com.gs.common.util.FactoryHelper;
import com.gs.common.util.GenericsUtil;
import com.gs.common.util.StringUtil;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.service.EmployeeService;
import com.gs.emp.vo.Employee;

public class MapperHelper {
	
	static Logger log = Logger.getLogger(MapperHelper.class);
	
	private ConcurrentHashMap<Integer, Object> recurringMap = new  ConcurrentHashMap<Integer, Object>();

	public MapperHelper() {
	}
	
	public MapperHelper(Mapper mapper) {
	}
	
	/*public Object mapImpl(Object sourceObj, Object destObj){

		if(null == sourceObj){return null;}
		
		if(null == destObj){destObj = GenericsUtil.getInstance(destObj.getClass());}
		if(null == destObj){return null;}
		
		fieldNameMap(sourceObj, destObj);
		
		return destObj;
	}*/

	private void mapUpdateContentImpl(Object sourceObj, Object destObj){

		if(null == sourceObj || null == destObj){return;}

		mapAll(sourceObj, destObj);
	}

	
	public void mapImpl(Object sourceObj, Object destObj){

		if(null == sourceObj || null == destObj){return;}
		
		//methodNameMap(sourceObj, destObj);
		//fieldNameMap(sourceObj, destObj);
		
		//mapSimpleInstanceImpl(sourceObj, destObj);
		
		mapAll(sourceObj, destObj);
	}
	
	private static void mapSimpleInstance(Object sourceObj, Object destObj){

		if(null == sourceObj || null == destObj){return;}
		
		mapSimpleInstanceImpl(sourceObj, destObj);
	}
	
	
	private void mapAll(Object sourceObj, Object destObj) {

		if(null == sourceObj || null == destObj){return;}

		fieldNameMapComplex(sourceObj,destObj);
	}	
	
	private void fieldNameMapComplex(Object sourceObj, Object destObj) {

		//Field[] srcFields = sourceObj.getClass().getDeclaredFields();
		//Field[] destFields = destObj.getClass().getDeclaredFields();

		Field[] srcFields =GenericsUtil.getAllFields(sourceObj);
		Field[] destFields = GenericsUtil.getAllFields(destObj);
		
		// Method[] outputMethods = destObj.getClass().getMethods();

		Map<String,Object> srcCmplxMap = new HashMap<String, Object>();
		List<Field> srcCmplxFields = new ArrayList<Field>(1);
		
		try{
			for(Field srcField:srcFields){
				//for(int count = 0; count < srcFields.length; count++){
				//Field inputField = srcFields[count];
				if(null == srcField){continue;}
				
				Class srcfieldTypeClass = srcField.getType();

				String fieldName = srcField.getName();

				// String methodName =
				// StringUtil.formFieldToMethodName(fieldName, "get");
				String methodName = FieldTypeCmd.createGetMethodName(srcField);

				if(StringUtils.isBlank(methodName)){ continue;}

				Object srcGetMethodVal = null;
					try{
						Method inputMethod = sourceObj.getClass().getMethod(methodName);

						srcGetMethodVal = inputMethod.invoke(sourceObj);

					}catch(Exception e){
						log.error("Exception invoke method " + methodName + " msg:"
								+ e.getMessage());
					}

				if(null == srcGetMethodVal){
					if(log.isDebugEnabled()){log.debug("NULL object. Not populated fieldName :"+fieldName);}
					continue;}
				
				if(GenericsUtil.isSimpleInstanceof(srcGetMethodVal)){
					populateSimpleInstance(destObj, srcGetMethodVal, fieldName);
				}else{
					srcCmplxFields.add(srcField);
					srcCmplxMap.put(fieldName, srcGetMethodVal);
				}
			}
			
			if(null != destObj){
				recurringMap.put(sourceObj.hashCode(), destObj);
			}
			
			for(Field srcCmplxField:srcCmplxFields){
				if(null== srcCmplxField){continue;}
				
				Class srcfieldTypeClass = srcCmplxField.getType();
				String srcFieldName = srcCmplxField.getName();
				
				Object srcGetMethodVal =  srcCmplxMap.get(srcFieldName);
				
				if(GenericsUtil.isCollectionInstance(srcGetMethodVal)){

					/*
					 * Type genericFieldType = inputField.getGenericType();
					 * 
					 * if(genericFieldType instanceof ParameterizedType){
					 * ParameterizedType aType = (ParameterizedType)
					 * genericFieldType; Type[] fieldArgTypes =
					 * aType.getActualTypeArguments(); for(Type fieldArgType :
					 * fieldArgTypes){ Class fieldArgClass = (Class)
					 * fieldArgType; System.out.println("fieldArgClass = " +
					 * fieldArgClass); } }
					 */
					if(srcGetMethodVal instanceof List){
						populateCmplxListInstance(destObj, srcGetMethodVal, srcFieldName);
					}else if(srcGetMethodVal instanceof Set){
						populateCmplxSetInstance(destObj, srcGetMethodVal, srcFieldName);
					} 
				}else if(null != srcGetMethodVal && null != destObj){
					//populateCustomInstance(destObj, srcGetMethodVal, fieldName);

					Class destFieldTypeClass = null;
					String destFieldName = null;
					Field destField = null;
					
					for(Field destFieldLo : destFields){
						
						destFieldName = destFieldLo.getName();
						
						if(null != destFieldLo && StringUtils.isNotBlank(srcFieldName)
								&& srcFieldName.equals(destFieldName)){
							destFieldTypeClass = destFieldLo.getType();
							destField = destFieldLo;
							break;
						}
					}

					Object complexOutObj = GenericsUtil.getInstance(destFieldTypeClass);
					
					if(null != recurringMap && recurringMap.size() > 0
							&& recurringMap.containsKey(srcGetMethodVal.hashCode())){
						
						/*String outMethodName = StringUtil.formFieldToMethodName(destFieldName, "set");
						Method outputMethod = destObj.getClass().getMethod(outMethodName, null);
						outputMethod.invoke(destObj,recurringMap.get(srcGetMethodVal.hashCode()));*/
						populateCmplxCustomInst(destObj,recurringMap.get(srcGetMethodVal.hashCode()),destFieldName);
					}else{
						fieldNameMapComplex(srcGetMethodVal, complexOutObj);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("mapper helper " + destObj);
	}

	private static Object populateCmplxCustomInst(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == getMethodObj || StringUtils.isBlank(fieldName)){
			return null;
		}

		Object setMethodObj = null; // outputObj.getClass().getMethod("set"+out)

		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		try{
			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();

			for(Method outputMethod : outputMethods){
				if(null == outputMethod){
					continue;
				}

				String methodName = outputMethod.getName();

				if(outMethodName.equalsIgnoreCase(methodName)){

					Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);

					if(outputClassType.isInstance(getMethodObj)){
						outputMethod.invoke(outputObj, getMethodObj);
						break;
					}
				}
			}
		}catch(IllegalArgumentException e){
			log.error("Exception invoke set method " + outMethodName + " msg:" + e.getMessage());
		}catch(Exception excep){
			log.error("Exception invoke set method " + outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}	
	
	private Object populateComplexObj(Object outputObj, Object inputMethodGetVal, String fieldName) {
		
		if(null == inputMethodGetVal || null == outputObj )return null;

		Object populateMethodObj = null;

		if(GenericsUtil.isSimpleInstanceof(inputMethodGetVal) ){
			populateMethodObj = populateSimpleInstance(outputObj, inputMethodGetVal, fieldName);
		}else if (inputMethodGetVal instanceof List){
			populateListInstance(outputObj,inputMethodGetVal,fieldName);
		}else if (inputMethodGetVal instanceof Map){
			/*populateSimpleInstance(outputObj, outputMethods, inputMethod,
			((BO)inputMethodGetVal).mapToDto());*/
	
		}else if (inputMethodGetVal instanceof Object){
			if(log.isTraceEnabled()){
				log.trace(" inputMethodGetVal.hashCode() ?:"+inputMethodGetVal.hashCode());
				log.trace(" recurringMap ?: "+recurringMap);
			}
			if(recurringMap.size() > 0 && recurringMap.containsKey(inputMethodGetVal.hashCode())){
				log.warn("inputMethodGetVal?: "+" is ALREADY populated");
				processRecurCustomInstance(outputObj, fieldName);
			}else{
				recurringMap.put(inputMethodGetVal.hashCode(),inputMethodGetVal);
				populateCustomInstance(outputObj, inputMethodGetVal, fieldName);
			}
			//populateCustomInstance(outputObj, inputMethodGetVal, fieldName);
		}
		return populateMethodObj;
	}	
		
	
	private static void mapSimpleInstanceImpl(Object sourceObj, Object destObj){

		//Field[] inputFields = sourceObj.getClass().getDeclaredFields();
		Field[] inputFields = GenericsUtil.getAllFields(sourceObj);

		try {
			for (int count = 0; count < inputFields.length; count++) {

				Field inputField = inputFields[count];
				String fieldName = inputField.getName();

				String methodName = FieldTypeCmd.createGetMethodName(inputField);

				if (StringUtils.isBlank(methodName)) {continue;}

				Object inputMethodGetVal = null;
					try {
						Method inputMethod = sourceObj.getClass().getMethod(methodName);
						
						inputMethodGetVal = inputMethod.invoke(sourceObj);
						
						if(GenericsUtil.isSimpleInstanceof(inputMethodGetVal) ){
							populateSimpleInstance(destObj, inputMethodGetVal, fieldName);
						}

					} catch (IllegalArgumentException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (IllegalAccessException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (InvocationTargetException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (Exception e){
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					}
			}
		} catch (Exception e) {
			log.error("exception occurred during simpleInstance mapped "+e.getMessage());
		}	
	}
	
	
	private void fieldNameMap(Object sourceObj, Object destObj){

		//Field[] inputFields = sourceObj.getClass().getDeclaredFields();
		//Method[] outputMethods = destObj.getClass().getMethods();
		Field[] inputFields = GenericsUtil.getAllFields(sourceObj);
		
		try {
			for (int count = 0; count < inputFields.length; count++) {

				Field inputField = inputFields[count];
				String fieldName = inputField.getName();

				//String methodName = StringUtil.formFieldToMethodName(fieldName, "get");
				String methodName = FieldTypeCmd.createGetMethodName(inputField);

				if (StringUtils.isBlank(methodName)) {continue;}
					
				Object inputMethodGetVal = null;

					try {
						Method inputMethod = sourceObj.getClass().getMethod(methodName);
						
						inputMethodGetVal = inputMethod.invoke(sourceObj);
						
						populateOutputObj(destObj, inputMethodGetVal,fieldName);

					} catch (IllegalArgumentException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (IllegalAccessException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (InvocationTargetException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (Exception e){
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("mapper helper " +destObj);
			
	}

	private void methodNameMap(Object sourceObj, Object destObj) {
		Method[] inputMethods = sourceObj.getClass().getDeclaredMethods();
		Method[] outputMethods = destObj.getClass().getMethods();
		
		try {
			for (int count = 0; count < inputMethods.length; count++) {

				Method inputMethod = inputMethods[count];
				String methodName = inputMethod.getName();

				Object inputMethodGetVal = null;
				if (null != inputMethod
						&& (methodName.startsWith("get")|| methodName.startsWith("is"))) {
					try {

						inputMethodGetVal = inputMethod.invoke(sourceObj);
						
						populateOutputObj(destObj, outputMethods, inputMethod, inputMethodGetVal);

					} catch (IllegalArgumentException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (IllegalAccessException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (InvocationTargetException e) {
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					} catch (Exception e){
						log.error("Exception invoke method "+ methodName + " msg:" + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("mapper helper " +destObj);
	}
	

	
	private Object populateOutputObj(Object outputObj, Object inputMethodGetVal, String fieldName) {
		
		if(null == inputMethodGetVal || null == outputObj )return null;

		Object populateMethodObj = null;

		if(GenericsUtil.isSimpleInstanceof(inputMethodGetVal) ){
			populateMethodObj = populateSimpleInstance(outputObj, inputMethodGetVal, fieldName);
		}else if (inputMethodGetVal instanceof List){
			populateListInstance(outputObj,inputMethodGetVal,fieldName);
		}else if (inputMethodGetVal instanceof Map){
			/*populateSimpleInstance(outputObj, outputMethods, inputMethod,
			((BO)inputMethodGetVal).mapToDto());*/
	
		}else if (inputMethodGetVal instanceof Object){
			if(log.isTraceEnabled()){
				log.trace(" inputMethodGetVal.hashCode() ?:"+inputMethodGetVal.hashCode());
				log.trace(" recurringMap ?: "+recurringMap);
			}
			if(recurringMap.size() > 0 && recurringMap.containsKey(inputMethodGetVal.hashCode())){
				log.warn("inputMethodGetVal?: "+" is ALREADY populated");
				processRecurCustomInstance(outputObj, fieldName);
			}else{
				recurringMap.put(inputMethodGetVal.hashCode(),inputMethodGetVal);
				populateCustomInstance(outputObj, inputMethodGetVal, fieldName);
			}
			//populateCustomInstance(outputObj, inputMethodGetVal, fieldName);
		}
		return populateMethodObj;
	}	
	
	private Object populateOutputObj(Object outputObj, Method[] outputMethods, Method inputMethod,
			Object inputMethodGetVal) {
		
		if(null == inputMethodGetVal || null == outputObj )return null;

		Object populateMethodObj = null;

		if(GenericsUtil.isSimpleInstanceof(inputMethodGetVal) ){
			populateMethodObj = populateSimpleInstance(outputObj, outputMethods, inputMethod,
					inputMethodGetVal);
		}else if (inputMethodGetVal instanceof Object){
			/*populateSimpleInstance(outputObj, outputMethods, inputMethod,
					((BO)inputMethodGetVal).mapToDto());*/
			
		}
		return populateMethodObj;
	}

	private Object populateSimpleInstance(Object outputObj, Method[] outputMethods,
			Method inputMethod, Object getMethodObj) {

		if(null == outputObj || null == getMethodObj){return null;}
		
		Object setMethodObj = null;  //outputObj.getClass().getMethod("set"+out)
		
		for (int outCnt = 0; outCnt < outputMethods.length; outCnt++) {

			Method outMethod = outputMethods[outCnt];
			
			String inputMthName = stripGetSetfromMethodName(inputMethod.getName());
			String outMethodName = stripGetSetfromMethodName(outMethod.getName());
			
			if (StringUtils.isNotBlank(inputMthName)&& StringUtils.isNotBlank(outMethodName)
					&& (inputMthName.equalsIgnoreCase(outMethodName)) && outMethod.getName().startsWith("set")) {
				try {
					
					//outMethod.invoke(destClass.cast(outputObj),sourceClass.cast(getMethodObj));
					outMethod.invoke(outputObj,getMethodObj);
					//outputObj.getClass().getMethod("set"+out)
					//outMethod.invoke
					break;
					
				} catch (IllegalArgumentException e) {
					log.error("Exception invoke set method "+ outMethodName + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.error("Exception invoke set method "+ outMethodName + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					log.error("Exception invoke set method "+ outMethodName + " msg:" + e.getMessage());
				}
			}
		}
		return setMethodObj;
	}

	private static Object populateSimpleInstance(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == outputObj || null == getMethodObj || StringUtils.isBlank(fieldName)){return null;}
		
		Object setMethodObj = null;  //outputObj.getClass().getMethod("set"+out)
		
		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		
		try {
			Method outMethod = outputObj.getClass().getMethod(outMethodName,getMethodObj.getClass());
			
			outMethod.invoke(outputObj,getMethodObj);

		} catch (Exception excep){
			  log.error("Exception invoke set method "+ outMethodName + " msg:" + excep.getMessage()); }

		return setMethodObj;
	}
	
	private Object populateCmplxSetInstance(Object outputObj, Object srcGetMethodList, String fieldName) {

		if(null == srcGetMethodList || StringUtils.isBlank(fieldName)){return null; }

		Object setMethodObj = null; // outputObj.getClass().getMethod("set"+out)

		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");

		try{

			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();
			for(Method outputMethod:outputMethods){

				if(null == outputMethod){continue;}
				
				String methodName = outputMethod.getName();

				if(outMethodName.equalsIgnoreCase(methodName)){
					Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);
					Set listInstance = new HashSet();

					for(Object srcGetMethodVal : (Set) srcGetMethodList){
						Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
						
						// Populate simple Instances
						this.fieldNameMapComplex(srcGetMethodVal, destSetMethodObj);
						
						if(null != destSetMethodObj){
							listInstance.add(destSetMethodObj);
						}
					}
					outputMethod.invoke(outputObj, listInstance);
					break;//terminate the loop
				}
			}
		}catch(IllegalArgumentException e){
			log.error("Exception invoke set method " + outMethodName + " msg:" + e.getMessage());
		}catch(Exception excep){
			log.error("Exception invoke set method " + outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}	
	
	
	private Object populateCmplxListInstance(Object outputObj, Object srcGetMethodList, String fieldName) {

		if(null == srcGetMethodList || StringUtils.isBlank(fieldName)){return null; }

		Object setMethodObj = null; // outputObj.getClass().getMethod("set"+out)

		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");

		try{

			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();
			for(Method outputMethod:outputMethods){

				if(null == outputMethod){continue;}
				
				String methodName = outputMethod.getName();

				if(outMethodName.equalsIgnoreCase(methodName)){
					Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);
					List listInstance = new ArrayList();

					for(Object srcGetMethodVal : (List) srcGetMethodList){
						Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
						
						// Populate simple Instances
						this.fieldNameMapComplex(srcGetMethodVal, destSetMethodObj);
						
						if(null != destSetMethodObj){
							listInstance.add(destSetMethodObj);
						}
					}
					outputMethod.invoke(outputObj, listInstance);
					break;//terminate the loop
				}
			}
		}catch(IllegalArgumentException e){
			log.error("Exception invoke set method " + outMethodName + " msg:" + e.getMessage());
		}catch(Exception excep){
			log.error("Exception invoke set method " + outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}	
	
	private Object populateListInstance(Object outputObj, Object srcGetMethodList, String fieldName) {

		if(null == srcGetMethodList || StringUtils.isBlank(fieldName)){return null; }

		Object setMethodObj = null; // outputObj.getClass().getMethod("set"+out)

		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");

		try{

			/* NOT WORKING
			 Method outputMethod = outputObj.getClass().getMethod(outMethodName, null);

			Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);
			List listInstance = new ArrayList();

			for(Object srcGetMethodVal : (List) srcGetMethodList){
				Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
				// Populate simple Instances
				this.fieldNameMap(srcGetMethodVal, destSetMethodObj);
				if(null != destSetMethodObj){
					listInstance.add(destSetMethodObj);
				}
			}
			outputMethod.invoke(outputObj, listInstance);*/
			
			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();
			for(int count = 0; count < outputMethods.length; count++){

				Method outputMethod = outputMethods[count];
				String methodName = outputMethod.getName();

				if(outMethodName.equalsIgnoreCase(methodName)){
					Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);
					List listInstance = new ArrayList();

					for(Object srcGetMethodVal : (List) srcGetMethodList){
						Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
						// Populate simple Instances
						this.fieldNameMap(srcGetMethodVal, destSetMethodObj);
						if(null != destSetMethodObj){
							listInstance.add(destSetMethodObj);
						}
					}
					outputMethod.invoke(outputObj, listInstance);
					break;//terminate the loop
				}
			}
		}catch(IllegalArgumentException e){
			log.error("Exception invoke set method " + outMethodName + " msg:" + e.getMessage());
		}catch(Exception excep){
			log.error("Exception invoke set method " + outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}	
	
	
	private Object populateCustomInstanceCmplx(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == getMethodObj || StringUtils.isBlank(fieldName)){return null;}
		
		Object setMethodObj = null;  //outputObj.getClass().getMethod("set"+out)
		
		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		
		// this is to check already instance is processed; if so get and set the
		// instance, instead process; 
		// mandatory method to avoid StackOverflowException 
		processRecurCustomInstance(outputObj, fieldName);
		
		try {
			/* NOT WORKING
			 Method outputMethod = outputObj.getClass().getMethod(outMethodName,null);
			
			Class[] outputMthParam = outputMethod.getParameterTypes();
			if(null != outputMthParam && outputMthParam.length > 0 && null != outputMthParam[0]){
				Class outputClassType = outputMthParam[0];
				setMethodObj = FactoryHelper.getInstance(outputClassType);
				
				//Populate simple Instances
				this.fieldNameMap(getMethodObj, setMethodObj);
				
				outputMethod.invoke(outputObj,setMethodObj);
				
				if(null != setMethodObj && !recurringMap.containsKey(setMethodObj.hashCode())){
					recurringMap.put(setMethodObj.hashCode(), setMethodObj); }
			}*/

			
			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();

			for (int count = 0; count < outputMethods.length; count++) {

				Method outputMethod = outputMethods[count];
				String methodName = outputMethod.getName();
				
				if(outMethodName.equalsIgnoreCase(methodName)){
					Class[] outputMthParam = outputMethod.getParameterTypes();
					if(null != outputMthParam && outputMthParam.length > 0 && null != outputMthParam[0]){
						Class outputClassType = outputMthParam[0];
						setMethodObj = FactoryHelper.getInstance(outputClassType);

						/*if(null != setMethodObj && !recurringMap.containsKey(setMethodObj.hashCode())){
							recurringMap.put(setMethodObj.hashCode(), setMethodObj); }	*/					
						
						//Populate simple Instances
						this.fieldNameMap(getMethodObj, setMethodObj);
						
						outputMethod.invoke(outputObj,setMethodObj);
					}
					break;
				}
				
			}
			
			//Method outMethod = outputObj.getClass().getMethod(outMethodName,getMethodObj.getClass());
			
		} catch (IllegalArgumentException e) {
			log.error("Exception invoke set method "+ outMethodName + " msg:" + e.getMessage());
		} catch (Exception excep){
			log.error("Exception invoke set method "+ outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}	
	
	private Object populateCustomInstance(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == getMethodObj || StringUtils.isBlank(fieldName)){return null;}
		
		Object setMethodObj = null;  //outputObj.getClass().getMethod("set"+out)
		
		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		
		// this is to check already instance is processed; if so get and set the
		// instance, instead process; 
		// mandatory method to avoid StackOverflowException 
		processRecurCustomInstance(outputObj, fieldName);
		
		try {
			/* NOT WORKING
			 Method outputMethod = outputObj.getClass().getMethod(outMethodName,null);
			
			Class[] outputMthParam = outputMethod.getParameterTypes();
			if(null != outputMthParam && outputMthParam.length > 0 && null != outputMthParam[0]){
				Class outputClassType = outputMthParam[0];
				setMethodObj = FactoryHelper.getInstance(outputClassType);
				
				//Populate simple Instances
				this.fieldNameMap(getMethodObj, setMethodObj);
				
				outputMethod.invoke(outputObj,setMethodObj);
				
				if(null != setMethodObj && !recurringMap.containsKey(setMethodObj.hashCode())){
					recurringMap.put(setMethodObj.hashCode(), setMethodObj); }
			}*/

			
			Method[] outputMethods = outputObj.getClass().getDeclaredMethods();

			for (int count = 0; count < outputMethods.length; count++) {

				Method outputMethod = outputMethods[count];
				String methodName = outputMethod.getName();
				
				if(outMethodName.equalsIgnoreCase(methodName)){
					Class[] outputMthParam = outputMethod.getParameterTypes();
					if(null != outputMthParam && outputMthParam.length > 0 && null != outputMthParam[0]){
						Class outputClassType = outputMthParam[0];
						setMethodObj = FactoryHelper.getInstance(outputClassType);

						/*if(null != setMethodObj && !recurringMap.containsKey(setMethodObj.hashCode())){
							recurringMap.put(setMethodObj.hashCode(), setMethodObj); }	*/					
						
						//Populate simple Instances
						this.fieldNameMap(getMethodObj, setMethodObj);
						
						outputMethod.invoke(outputObj,setMethodObj);
					}
					break;
				}
				
			}
			
			//Method outMethod = outputObj.getClass().getMethod(outMethodName,getMethodObj.getClass());
			
		} catch (IllegalArgumentException e) {
			log.error("Exception invoke set method "+ outMethodName + " msg:" + e.getMessage());
		} catch (Exception excep){
			log.error("Exception invoke set method "+ outMethodName + " msg:" + excep.getMessage());
		}

		return setMethodObj;
	}

	private void processRecurCustomInstance(Object outputObj, String fieldName) {
		String outGetMethodName = StringUtil.formFieldToMethodName(fieldName, "get");
		String outSetMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		try {
			Method outputMethod = outputObj.getClass().getMethod(outGetMethodName,null);
			Object outGetMethodVal = outputMethod.invoke(outputObj);
			
			if(null == outGetMethodVal){
				log.warn(" getter "+fieldName+" is not available.");
				return;}
			
			if(log.isTraceEnabled()){
				log.trace(outGetMethodName+" object is available. its hashCode "+outGetMethodVal.hashCode());
				log.trace(" recurringMap ?: "+recurringMap);	
			}
			
			if(recurringMap.containsKey(outGetMethodVal.hashCode())){
				Method outputSetMethod = outputObj.getClass().getMethod(outSetMethodName,null);
				outputSetMethod.invoke(outputObj,outGetMethodVal);
				if(log.isDebugEnabled()){
					log.debug("invoke >"+outSetMethodName+"< and populated with "+outGetMethodVal.toString());	}				
			}
		}catch(Exception e){log.warn(" getter "+fieldName+" is not available "+e.getMessage());}
	}	
	
	private String stripGetSetfromMethodName(String methodName) {
		
		String inputMthName = "";
		
		if(StringUtils.isBlank(methodName)){return inputMthName;}
		
		if(methodName.startsWith("get") || methodName.startsWith("set") ){
			inputMthName = methodName.substring(3);
		}else if(methodName.startsWith("is")){
			inputMthName = methodName.substring(2);
		}
		return inputMthName;
	}
}
