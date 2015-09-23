package com.gs.common.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.command.FieldTypeCmd;
import com.gs.common.util.FactoryHelper;
import com.gs.common.util.GenericsUtil;
import com.gs.common.util.StringUtil;

@SuppressWarnings({"rawtypes","unchecked"})
public class MapperImplHelper {

	static Logger log = Logger.getLogger(MapperImplHelper.class);

	private ConcurrentHashMap<Integer, Object> recurringMap = new ConcurrentHashMap<Integer, Object>();

	public static void map(Object sourceObj, Object destObj) {

		if(null == sourceObj || null == destObj){
			return;
		}
		new MapperImplHelper().mapImpl(sourceObj, destObj);
		
		if(log.isTraceEnabled()){
			log.trace("existing map sourceObj" + sourceObj.toString());
			log.trace("existing map destObj" + destObj.toString());
		}		
		System.out.println(" sourceObj : "+sourceObj.toString());
		System.out.println(" destObj : "+destObj.toString());
	}
	
	private void mapImpl(Object sourceObj, Object destObj){
		populateSimpleInstAll(sourceObj, destObj);
		populateComplexInstAll(sourceObj, destObj);
		populateCollectionInstAll(sourceObj, destObj);
	}

	private void populateSimpleInstAll(Object sourceObj, Object destObj) {

		Field[] srcFields = GenericsUtil.getAllFields(sourceObj);

		for(Field srcField : srcFields){
			if(null == srcField){
				continue;
			}

			if(GenericsUtil.isSimpleInstanceof(srcField.getType())){
				populateSimpleInstances(sourceObj, destObj,srcField);
			}
		}
		if(null != destObj){
			recurringMap.put(sourceObj.hashCode(), destObj);
		}
	}

	private void populateComplexInstAll(Object sourceObj, Object destObj) {

		Field[] srcFields = GenericsUtil.getAllFields(sourceObj);

		for(Field srcField : srcFields){
			if(null == srcField){
				continue;}

			if(GenericsUtil.isComplexInstance(srcField.getType())){
				populateComplexInst(sourceObj, destObj, srcField);
				/*if(null != destObj){
					recurringMap.put(sourceObj.hashCode(), destObj);//Over write one round of population
				}*/					
			}
		}
		if(log.isTraceEnabled()){
			log.trace("existing populateComplexInstAll" + destObj.toString());
		}		
	}

	
	private void populateCollectionInstAll(Object sourceObj, Object destObj) {

		Field[] srcFields = GenericsUtil.getAllFields(sourceObj);

		for(Field srcField : srcFields){
			if(null == srcField){
				continue;
			}

			if(GenericsUtil.isCollectionInstance(srcField.getType())){
				populateCollectionInstances(destObj,sourceObj,srcField.getName());
			}
		}
		if(log.isTraceEnabled()){
			log.trace("existing populatecollectionInstAll" + destObj.toString());
		}
	}		
	

	
	private void populateComplexInst(Object sourceObj, Object destObj, Field srcField){

		if(null == sourceObj || null == destObj || null == srcField){
			return;
		}
		
		String srcComplexFieldName = srcField.getName();

		String methodName = FieldTypeCmd.createGetMethodName(srcField);

		if(StringUtils.isBlank(methodName)){
			return; }

		Object srcGetMethodVal = null;
		try{

			Method inputMethod = sourceObj.getClass().getMethod(methodName);
			srcGetMethodVal = inputMethod.invoke(sourceObj);

		}catch(Exception e){
			log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
		}

		if(null != srcGetMethodVal){
			if(null != recurringMap && recurringMap.size() > 0
					&& recurringMap.containsKey(srcGetMethodVal.hashCode())){

				populateComplexInstExists(destObj,
						recurringMap.get(srcGetMethodVal.hashCode()), srcComplexFieldName);
			}else{
				
				Field destComplexField = GenericsUtil.getMatchField(destObj, srcComplexFieldName);
				
				if(null != destComplexField){
					Object complexOutObj = null;
			
					try{
						complexOutObj = GenericsUtil.invokeGetMethod(destObj, GenericsUtil.getMatchMethod(destObj, methodName));
					}catch(Exception e){
						log.error("Exception in update content for" + methodName + " msg:" + e.getMessage());
					}
					if(null == complexOutObj ){
						complexOutObj = GenericsUtil.getInstance(destComplexField.getType());
					}

					mapImpl(srcGetMethodVal, complexOutObj); //Recursive Map call
					populateComplexInstExists(destObj,complexOutObj, srcComplexFieldName);
					
				}else{
					log.error("NOT populated source Complex field "+srcComplexFieldName);
				}
			}
		}
		if(log.isTraceEnabled()){
			log.trace("existing populateComplexInst" + destObj.toString());
		}
	}
	
	public void populateComplexInstExists(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == getMethodObj || StringUtils.isBlank(fieldName)){
			return;
		}

		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");

		try{
			Method outputMethod = GenericsUtil.getMatchMethod(outputObj, outMethodName);

			if(null == outputMethod){return;}
			
			Class outputClassType = GenericsUtil.getMethodParameterArgType(outputMethod);

			if(null != outputClassType && outputClassType.isInstance(getMethodObj)){
				outputMethod.invoke(outputObj, getMethodObj);
			}
		}catch(Exception excep){
			log.error("Exception invoke set method " + outMethodName + " msg:" + excep.getMessage());
		}
		
		if(log.isTraceEnabled()){
			log.trace("existing populateComplexInstExists " + outputObj.toString());
		}		
	}
	
	public void populateCollectionInstances(Object destObject, Object srcObject,
			String fieldName) {

		if(null == srcObject || StringUtils.isBlank(fieldName)){
			return;
		}

		String srGetMethodName = StringUtil.formFieldToMethodName(fieldName, "get");

		try{

			Method srcGetMethod = GenericsUtil.getMatchMethod(srcObject, srGetMethodName);
			Object srcGetMethodVal = GenericsUtil.invokeGetMethod(srcObject, srcGetMethod);
			
			if(null != srcGetMethodVal && srcGetMethodVal instanceof List){
				populateDestList(srcGetMethodVal, destObject,fieldName);
				
			}else if(null != srcGetMethodVal && srcGetMethodVal instanceof Set){
				/*Set setInstance = populateDestSet(srcGetMethodVal, destObject, destClassType);
				destSetMethod.invoke(destObject, setInstance);*/
				populateDestSet(srcGetMethodVal, destObject,fieldName);
			}

		}catch(Exception excep){
			log.error("Exception invoke set method " + fieldName + " msg:" + excep.getMessage());
		}
		if(log.isTraceEnabled()){
			log.trace("existing populateCollectionInstances " + destObject.toString());
		}			
	}

	/*private Set populateDestSet(Object srcGetMethodList, Class outputClassType){
		Set listInstance = new HashSet();

		for(Object srcGetMethodVal : (Set) srcGetMethodList){
			Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
			
			mapImpl(srcGetMethodVal, destSetMethodObj);//Recursive Map call
			
			if(null != destSetMethodObj){
				listInstance.add(destSetMethodObj);
			}
		}
		return listInstance;
	}*/
	
	private void populateDestSet(Object srcGetMethodSet, Object destObject, String fieldName) {
		
		if(null == srcGetMethodSet || null == destObject || StringUtils.isBlank(fieldName) || !(srcGetMethodSet instanceof Set)){
			log.warn("populateDestSet mandatory conditions are not satisfies");
			return;}

		String destSetMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		Method destSetMethod = GenericsUtil.getMatchMethod(destObject, destSetMethodName);
		Class destClassType = GenericsUtil.getMethodParameterArgType(destSetMethod);

		String destGetMethodName = StringUtil.formFieldToMethodName(fieldName, "get");

		Set setInstance = new HashSet();

		Object destGetMethodSetObj = null;
		Set destGetMethodSetVal = null;

		try{
			destGetMethodSetObj = GenericsUtil.invokeGetMethod(destObject, fieldName);
			
			if(null != destGetMethodSetObj && destGetMethodSetObj instanceof Set
					&& ((Set) destGetMethodSetObj).size() > 0){
				destGetMethodSetVal = (Set) destGetMethodSetObj;
			}

		}catch(Exception e){
			log.error("Exception in update content for" + destGetMethodName + " msg:"
					+ e.getMessage());
		}

		for(Object srcGetMethodVal : (Set) srcGetMethodSet){

			boolean aLookupIsSame = false;

			Object destGetMethodValueLo = null;

			if(null == srcGetMethodVal){
				continue;
			}
			if(null != destGetMethodSetVal){
				for(Object destGetMethodVal : destGetMethodSetVal){
					if(null == destGetMethodVal){
						continue;
					}
	
					aLookupIsSame = isSimilarObjs(srcGetMethodVal, destGetMethodVal);
	
					if(aLookupIsSame){
						destGetMethodValueLo = destGetMethodVal;
						break;
					}
				}
			}

			if(aLookupIsSame){
				mapImpl(srcGetMethodVal, destGetMethodValueLo);// Recursive Map call
				setInstance.add(destGetMethodValueLo);
			}else{
				
				Object destSetMethodObj = FactoryHelper.getInstance(destClassType);
				mapImpl(srcGetMethodVal, destSetMethodObj); // Recursive Map call
				
				if(null != destSetMethodObj){
					setInstance.add(destSetMethodObj);
				}
			}

		}

		try{
			destSetMethod.invoke(destObject, setInstance);
		}catch(Exception excep){
			log.error("Exception: collection set method invoke " + excep.getMessage());
		}
	}	
	
	private void populateDestList(Object srcGetMethodList, Object destObject, String fieldName) {
		
		if(null == srcGetMethodList || null == destObject || StringUtils.isBlank(fieldName) || !(srcGetMethodList instanceof Set)){
			log.warn("populateDestSet mandatory conditions are not satisfies");
			return;}
		
		String destSetMethodName = StringUtil.formFieldToMethodName(fieldName, "set");			
		Method destSetMethod = GenericsUtil.getMatchMethod(destObject, destSetMethodName);
		Class destClassType = GenericsUtil.getMethodParameterArgType(destSetMethod);

		String destGetMethodName = StringUtil.formFieldToMethodName(fieldName, "get");			

		List listInstance = new ArrayList();

		Object destGetMethodListObj = null;
		List destGetMethodListVal = null;
		
		try{
			destGetMethodListObj = GenericsUtil.invokeGetMethod(destObject, GenericsUtil.getMatchMethod(destObject, destGetMethodName));
			if(null != destGetMethodListObj && destGetMethodListObj instanceof List && ((List)destGetMethodListObj).size() > 0){
				destGetMethodListVal = (List)destGetMethodListObj;
			}

		}catch(Exception e){
			log.error("Exception in update content for" + destGetMethodName + " msg:" + e.getMessage());
		}

		/*if(null != destGetMethodListVal){*/
			for(Object srcGetMethodVal : (List) srcGetMethodList){

				boolean aLookupIsSame = false;

				//Object srcGetMethodValueLo = srcGetMethodVal;
				Object destGetMethodValueLo = null;

				if(null == srcGetMethodVal){
					continue;
				}
				for(Object destGetMethodVal : (List) destGetMethodListVal){
					if(null == destGetMethodVal){
						continue;
					}
					
					aLookupIsSame = isSimilarObjs(srcGetMethodVal, destGetMethodVal);

					if(aLookupIsSame){
						destGetMethodValueLo = destGetMethodVal;
						break;
					}
				}
				
				if(aLookupIsSame){
					mapImpl(srcGetMethodVal, destGetMethodValueLo);//Recursive Map call
					listInstance.add(destGetMethodValueLo);
				}else{
					Object destSetMethodObj = FactoryHelper.getInstance(destClassType);
					mapImpl(srcGetMethodVal, destSetMethodObj); //Recursive Map call
					if(null != destSetMethodObj){
						listInstance.add(destSetMethodObj);
					}					
				}

			}
		/*}else{
			
			for(Object srcGetMethodVal : (List) srcGetMethodList){
				Object destSetMethodObj = FactoryHelper.getInstance(destClassType);
				
				mapImpl(srcGetMethodVal, destSetMethodObj); //Recursive Map call
				
				if(null != destSetMethodObj){
					listInstance.add(destSetMethodObj);
				}
			}
			try{
				destSetMethod.invoke(destObject, listInstance);
			}catch(Exception excep){
				log.error("Exception: collection set method invoke "+excep.getMessage());
			}
		}*/
		
		try{
			destSetMethod.invoke(destObject, listInstance);
		}catch(Exception excep){
			log.error("Exception: collection set method invoke "+excep.getMessage());
		}
		//return listInstance;
	}

	private boolean isSimilarObjs(Object srcGetMethodVal, 
			Object destGetMethodVal) {
		
		boolean aLookupIsSame = false;
		
		if(null == srcGetMethodVal || null == destGetMethodVal){return false;}
		
		Field[] srcFields = GenericsUtil.getAllFields(srcGetMethodVal);
		
		for(Field srcField:srcFields){
			String fieldNameLo = srcField.getName();
			if(GenericsUtil.isSimpleInstanceof(srcField.getType())
					&& !(Date.class == srcField.getType())){
				Object srcGetMethodReturns = GenericsUtil.invokeGetMethod(srcGetMethodVal, fieldNameLo);
				Object destGetMethodReturns = GenericsUtil.invokeGetMethod(destGetMethodVal, fieldNameLo);
				
				if(null != srcGetMethodReturns && null != destGetMethodReturns && destGetMethodReturns.equals(srcGetMethodReturns)){
					aLookupIsSame = true;
					if(log.isTraceEnabled()){log.trace("Content match feild "+ fieldNameLo+" and value :" +srcGetMethodReturns);}
					break;
				}
			}
		}
		return aLookupIsSame;
	}	
	
	private void populateSimpleInstances(Object sourceObj, Object destObj, Field srcField) {

		String fieldName = srcField.getName();

		String methodName = FieldTypeCmd.createGetMethodName(srcField);

		if(StringUtils.isBlank(methodName)){
			return;
		}

		Object srcGetMethodVal = null;
		try{

			Method inputMethod = sourceObj.getClass().getMethod(methodName);
			srcGetMethodVal = inputMethod.invoke(sourceObj);

		}catch(Exception e){
			log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
		}

		if(null != srcGetMethodVal){
			try{

				populateSimpleInstImpl(destObj, srcGetMethodVal, fieldName);

			}catch(Exception e){
				log.error("Exception when map method " + methodName + " msg:" + e.getMessage());
			}
		}else{
			if(log.isTraceEnabled()){
				log.trace("NULL object. Not populated fieldName :" + fieldName);
			}
			return;
		}
	}
	
	public void populateSimpleInstImpl(Object outputObj, Object getMethodObj, String fieldName) {

		if(null == outputObj || null == getMethodObj || StringUtils.isBlank(fieldName)){return;}
		
		String outMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		
		try {
			Method outMethod = outputObj.getClass().getMethod(outMethodName,getMethodObj.getClass());
			
			outMethod.invoke(outputObj,getMethodObj);

		} catch (Exception excep){
			  log.error("Exception invoke set method "+ outMethodName + " msg:" + excep.getMessage()); }
	}
}
