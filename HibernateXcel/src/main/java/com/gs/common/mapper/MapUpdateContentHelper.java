package com.gs.common.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gs.common.command.FieldTypeCmd;
import com.gs.common.util.FactoryHelper;
import com.gs.common.util.GenericsUtil;
import com.gs.common.util.StringUtil;

@SuppressWarnings({"rawtypes","unchecked"})
public class MapUpdateContentHelper {

	static Logger log = Logger.getLogger(MapUpdateContentHelper.class);

	private ConcurrentHashMap<Integer, Object> recurringMap = new ConcurrentHashMap<Integer, Object>();

	public static void map(Object sourceObj, Object destObj) {

		if(null == sourceObj || null == destObj){
			return;
		}
		new MapUpdateContentHelper().mapImpl(sourceObj, destObj);
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
			}
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
					Object complexOutObj = GenericsUtil.getInstance(destComplexField.getType());	

					mapImpl(srcGetMethodVal, complexOutObj); //Recursive Map call
					
				}else{
					log.error("NOT populated source Complex field "+srcComplexFieldName);
				}
			}
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
	}
	
	public void populateCollectionInstances(Object destObject, Object srcObject,
			String fieldName) {

		if(null == srcObject || StringUtils.isBlank(fieldName)){
			return;
		}

		String destSetMethodName = StringUtil.formFieldToMethodName(fieldName, "set");
		String srGetMethodName = StringUtil.formFieldToMethodName(fieldName, "get");

		try{

			Method srcGetMethod = GenericsUtil.getMatchMethod(srcObject, srGetMethodName);
			Object srcGetMethodVal = GenericsUtil.invokeGetMethod(srcObject, srcGetMethod);
			
			Method destSetMethod = GenericsUtil.getMatchMethod(destObject, destSetMethodName);

			Class destClassType = GenericsUtil.getMethodParameterArgType(destSetMethod);

			if(null != srcGetMethodVal && srcGetMethodVal instanceof List){
				List listInstance = populateDestList(srcGetMethodVal, destClassType);
				destSetMethod.invoke(destObject, listInstance);
			}else if(null != srcGetMethodVal && srcGetMethodVal instanceof Set){
				Set listInstance = populateDestSet(srcGetMethodVal, destClassType);
				destSetMethod.invoke(destObject, listInstance);
			}

		}catch(Exception excep){
			log.error("Exception invoke set method " + destSetMethodName + " msg:" + excep.getMessage());
		}
	}

	private Set populateDestSet(Object srcGetMethodList, Class outputClassType){
		Set listInstance = new HashSet();

		for(Object srcGetMethodVal : (Set) srcGetMethodList){
			Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
			
			mapImpl(srcGetMethodVal, destSetMethodObj);//Recursive Map call
			
			if(null != destSetMethodObj){
				listInstance.add(destSetMethodObj);
			}
		}
		return listInstance;
	}
	
	private List populateDestList(Object srcGetMethodList, Class outputClassType) {
		List listInstance = new ArrayList();

		for(Object srcGetMethodVal : (List) srcGetMethodList){
			Object destSetMethodObj = FactoryHelper.getInstance(outputClassType);
			
			mapImpl(srcGetMethodVal, destSetMethodObj); //Recursive Map call
			
			if(null != destSetMethodObj){
				listInstance.add(destSetMethodObj);
			}
		}
		return listInstance;
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
