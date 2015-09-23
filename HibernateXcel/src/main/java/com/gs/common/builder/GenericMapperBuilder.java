package com.gs.common.builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericMapperBuilder {

	private Object objInput;
	private Object objOutput;
	private Class inputClass;
	private Class outputClass;
	
	
	public GenericMapperBuilder() {
	}
	
	public GenericMapperBuilder(Object input, Object output) {
		this.objInput=input;
		this.objOutput=output;
		if(null != objInput){
			this.inputClass = objInput.getClass();
		}
		if(null != objOutput){
			this.outputClass = objOutput.getClass();
		}
	}

	public Object build() {
		if(null == inputClass || null == outputClass){
			return null;
		}
		Method[] inputMethods = inputClass.getMethods();
		Method[] outputMethods = outputClass.getMethods();
		try {
			for (int count = 0; count < inputMethods.length; count++) {

				Method methodLo = inputMethods[count];
				String methodName = methodLo.getName();

				Object getMethodObj = null;
				if (null != methodLo
						&& (methodLo.getName().startsWith("get") || methodLo
								.getName().startsWith("is"))) {
					try {
						getMethodObj = methodLo.invoke(objInput);
						
						populateOutputObj(outputMethods, methodLo, getMethodObj);

					} catch (IllegalArgumentException e) {
						System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
					} catch (IllegalAccessException e) {
						System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
					} catch (InvocationTargetException e) {
						System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	private void populateOutputObj(Method[] outputMethods, Method methodLo,
			Object getMethodObj) {
		
		
		if(isSimpleInstanceof(getMethodObj) ){
			getMethodObj = populateSimpleInstance(outputMethods, methodLo,
					getMethodObj);
		}else if(isSimpleCollectionInstanceof(getMethodObj) ){
			
		}
		
		getMethodObj = populateSimpleInstance(outputMethods, methodLo,
				getMethodObj);
	}

	private Object populateSimpleInstance(Method[] outputMethods,
			Method methodLo, Object getMethodObj) {
		for (int outCnt = 0; outCnt < outputMethods.length; outCnt++) {

			Method outMethodLo = outputMethods[outCnt];
			String outMethodName = outMethodLo.getName();

			Object setMethodObj = null;
			if (null != methodLo
					&& (methodLo.getName().startsWith("set"))) {
				try {
					getMethodObj = methodLo.invoke(objInput,getMethodObj);
				} catch (IllegalArgumentException e) {
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				}
			}
		}
		return getMethodObj;
	}

	private boolean isSimpleInstanceof(Object getMethodObj) {
		return getMethodObj instanceof String
				|| getMethodObj instanceof java.util.Date
				|| getMethodObj instanceof Integer
				|| getMethodObj instanceof Long
				|| getMethodObj instanceof Float
				|| getMethodObj instanceof Double;
	}
	
	private boolean isSimpleCollectionInstanceof(Object getMethodObj) {
		return isSimpleMapInstanceof (getMethodObj)
				|| getMethodObj instanceof java.util.List
				|| getMethodObj instanceof java.util.Set;
	}

	private boolean isSimpleMapInstanceof(Object getMethodObj) {
		if(null != getMethodObj && getMethodObj instanceof java.util.Map){
			java.util.Map mapLo = (java.util.Map)getMethodObj;
			if(!mapLo.isEmpty()){
				Class classLo = mapLo.values().iterator().next().getClass();
				if(classLo.isInstance(new String()) || classLo.isInstance(new Integer(0))){
			}
				return true;
			}
		}
			return false;
	}
}
