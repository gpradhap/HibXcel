package com.gs.emp.bo.pojoxml;

import com.gs.emp.bo.pojo.AttrLookupGroupPojo;
import com.gs.emp.dao.AttrLookupGroupDao;
import com.pg.utils.PojoXmlGenerate;

public class AttrLookupGroupPojoXml extends PojoXmlGenerate{
	
	public AttrLookupGroupPojoXml() {
		super.pojoType = AttrLookupGroupPojo.class; 
		super.objSource = new AttrLookupGroupPojo(new AttrLookupGroupDao().retrieveAll());
	}
	@Override
	public Object retrieveObjSrc() {
		// TODO Auto-generated method stub
		return null;
	}
}
