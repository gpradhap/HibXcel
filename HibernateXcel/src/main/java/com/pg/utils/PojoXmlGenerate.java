package com.pg.utils;

import java.util.HashMap;
import java.util.Map;

import com.gs.common.bo.AttrLookupGroupBo;

public abstract class PojoXmlGenerate {

	public Class pojoType = null;
	public Object objSource = null;
	public Class srcType = null;

	public void printOrShow() {
		pojoToXmlGenerate(pojoType);
		Map attrLookupGrpMap = xmlToPojoGenerate(pojoType);

		if (pojoType.isInstance(attrLookupGrpMap.get(pojoType.toString()))) {
			pojoType.cast(attrLookupGrpMap.get(pojoType.getSimpleName()));

		}
	}

	public void pojoToXmlGenerate() {
		if(objSource == null){
			objSource = retrieveObjSrc();
		}
		if (pojoType == null || objSource == null) {
			return;
		}
		try {
			XmlPojoConvertUtilImpl convertUtil = new XmlPojoConvertUtilImpl(
					pojoType,srcType);
			convertUtil.setObjSource(objSource);
			convertUtil.pojoToXmlConvert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pojoToXmlGenerate(Object objSource) {
		if(objSource == null){
			objSource = retrieveObjSrc();
		}
		if (pojoType == null || objSource == null) {
			return;
		}

		try {
			XmlPojoConvertUtilImpl convertUtil = new XmlPojoConvertUtilImpl(
					pojoType,srcType);
			convertUtil.setObjSource(objSource);
			convertUtil.pojoToXmlConvert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Map<String, Object> xmlToPojoGenerate() {
		if (pojoType == null) {
			return null;
		}
		Map<String, Object> xmlToPojo = null;
		try {
			xmlToPojo = new HashMap<String, Object>();
			XmlPojoConvertUtil convertUtil = new XmlPojoConvertUtilImpl(pojoType,
					srcType);
			Object obj = convertUtil.xmlToPojoConvert();
			xmlToPojo.put(srcType.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlToPojo;
	}

	public static void pojoToXmlGenerate(Class... classType) {
		if (classType == null) {
			return;
		}
		for (int index = 0; index < classType.length; index++) {
			if (classType[index].equals(AttrLookupGroupBo.class)) {
				AttrLookupGroupBo attrLookupGroup = new AttrLookupGroupBo(
						"EMP_PERS");
				XmlPojoConvertUtilImpl convertUtil = new XmlPojoConvertUtilImpl(AttrLookupGroupBo.class,
						AttrLookupGroupBo.class);
				convertUtil.setObjSource(attrLookupGroup);
				convertUtil.pojoToXmlConvert();
			}
		}

	}

	public static Map<String, Object> xmlToPojoGenerate(Class... classType) {
		if (classType == null) {
			return null;
		}
		Map<String, Object> xmlToPojo = new HashMap<String, Object>();
		for (int index = 0; index < classType.length; index++) {

			if (classType[index].equals(AttrLookupGroupBo.class)) {
				XmlPojoConvertUtil convertUtil = new XmlPojoConvertUtilImpl(AttrLookupGroupBo.class,
						AttrLookupGroupBo.class);
				Object obj = convertUtil.xmlToPojoConvert();
				xmlToPojo.put(AttrLookupGroupBo.class.toString(), obj);
			}

		}
		return xmlToPojo;
	}

	public abstract Object retrieveObjSrc();
	
}
