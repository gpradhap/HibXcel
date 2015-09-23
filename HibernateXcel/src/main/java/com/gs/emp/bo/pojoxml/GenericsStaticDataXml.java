package com.gs.emp.bo.pojoxml;

import com.gs.emp.bo.pojo.AttrLookupGroupPojo;
import com.gs.emp.bo.pojo.GenericsStaticData;
import com.gs.emp.bo.pojo.GenericsStaticDataPojo;
import com.gs.emp.dao.AttrLookupGroupDao;
import com.gs.emp.dao.GenericsStaticDataDao;
import com.pg.utils.PojoXmlGenerate;

public class GenericsStaticDataXml<T> extends PojoXmlGenerate{

	private final Class<T> tType = null;
	
	public GenericsStaticDataXml(Class<T> tType) {
		super.pojoType = tType; 
		super.srcType = tType;
		//super.objSource = new AttrLookupGroupPojo(new AttrLookupGroupDao().retrieveAll());
		//super.objSource = new GenericsStaticDataPojo(new GenericsStaticDataDao<T>(tType).retrieveAll());

	}
	
	public GenericsStaticDataXml(Class<T> tType, Class srcClassType) {
		super.pojoType = tType; 
		super.srcType = srcClassType;
		//super.objSource = new AttrLookupGroupPojo(new AttrLookupGroupDao().retrieveAll());
		//super.objSource = new GenericsStaticDataPojo(new GenericsStaticDataDao<T>(tType).retrieveAll());

	}	
	
	@Override
	public Object retrieveObjSrc() {
		super.objSource = new GenericsStaticDataDao<T>(srcType).retrieveAll();
		return objSource;
	}
}
