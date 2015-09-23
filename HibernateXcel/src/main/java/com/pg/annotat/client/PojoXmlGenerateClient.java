package com.pg.annotat.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.emp.bo.pojo.GenericsStaticDataPojo;
import com.gs.emp.bo.pojoxml.GenericsStaticDataPojoXml;
import com.gs.emp.dao.GenericsStaticDataDao;

public class PojoXmlGenerateClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<AttrLookupGroupBo> attrLookupGroupLst = new ArrayList<AttrLookupGroupBo>();
		//AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_PERS");
		
		//attrLookupGroupLst.add(attrLookupGroup);
		
		/*Map<String, Object> pojoMap = new AttrLookupGroupPojoXml().xmlToPojoGenerate();
		Object attrLookupGroupPojoObj = pojoMap.get(AttrLookupGroupPojo.class.toString());
		if(null != attrLookupGroupPojoObj && attrLookupGroupPojoObj instanceof AttrLookupGroupPojo){
			AttrLookupGroupPojo attrLookupGroupPojo = (AttrLookupGroupPojo) attrLookupGroupPojoObj;
			new AttrLookupGroupDao().saveOrUpdate(attrLookupGroupPojo.getAttrLookupGroupList());
		}
		*/
		
		//new AttrLookupGroupPojoXml().pojoToXmlGenerate();
		
		/*Class srcType = AttrLookupGroup.class;
		//Class pojoType = new GenericsStaticDataPojo<AttrLookupGroup>(srcType).getClass();
		new GenericsStaticDataXml<AttrLookupGroup>(srcType).pojoToXmlGenerate();*/

		Class srcType = null; 
		Class pojoType = null;
		Map<String, Object> pojoMap =null; 
		Object pojoObj = null;
		
		/****** AttributeLookup  ******/		
		srcType = AttrLookupGroupBo.class;
		pojoType = new GenericsStaticDataPojo<AttrLookupGroupBo>(srcType).getClass();
		
		//new GenericsStaticDataPojoXml<GenericsStaticDataPojo<AttrLookupGroupBo>>(pojoType,srcType).pojoToXmlGenerate();

		/*pojoMap = new GenericsStaticDataPojoXml<GenericsStaticDataPojo<AttrLookupGroupBo>>(pojoType,srcType).xmlToPojoGenerate();
		pojoObj = pojoMap.get(srcType.toString());
		if(null != pojoObj && pojoType.isInstance(pojoObj)){
			GenericsStaticDataPojo<AttrLookupGroupBo> genericPojo = (GenericsStaticDataPojo<AttrLookupGroupBo>)pojoObj;
			new GenericsStaticDataDao<AttrLookupGroupBo>(srcType).saveOrUpdate(genericPojo.gettList());
		}*/
		
		
		/****** AttributeLookup  ******/
		srcType = AttributeLookupBo.class;
		pojoType = new GenericsStaticDataPojo<AttributeLookupBo>(srcType).getClass();
		
		//new GenericsStaticDataPojoXml<GenericsStaticDataPojo<AttributeLookupBo>>(pojoType,srcType).pojoToXmlGenerate();

		
		pojoMap = new GenericsStaticDataPojoXml<GenericsStaticDataPojo<AttributeLookupBo>>(pojoType,srcType).xmlToPojoGenerate();
		pojoObj = pojoMap.get(srcType.toString());
		

		if(null != pojoObj && pojoType.isInstance(pojoObj)){
			GenericsStaticDataPojo<AttributeLookupBo> genericPojo = (GenericsStaticDataPojo<AttributeLookupBo>)pojoObj;
			new GenericsStaticDataDao<AttributeLookupBo>(srcType).saveOrUpdate(genericPojo.gettList());
		}
		
	}

}
