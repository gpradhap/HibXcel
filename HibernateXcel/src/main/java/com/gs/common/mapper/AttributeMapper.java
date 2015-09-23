package com.gs.common.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.common.vo.AttributeLookup;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.vo.Employee;

public class AttributeMapper<T extends Object> implements Mapper {

	static Logger log = Logger.getLogger(AttributeMapper.class);

	private static Boolean mapSuccessful = false;
	private static Object populatedObject = null;

	private ConcurrentHashMap<Integer, Object> recursiveMap = new ConcurrentHashMap<Integer, Object>();
	
	@Override
	public Object getMappedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper getMapperInstance(Class mapperClass) {
		return null;
	}

	@Override
	public Boolean isMapSuccess() {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeLookupBo map(AttributeLookup sourceObject) {

		
		AttributeLookupBo attributeLookupBo = new AttributeLookupBo();

		Mapper.genericMapper.map(sourceObject, attributeLookupBo);
		
		return attributeLookupBo;
	}
	
	public AttributeLookup map(AttributeLookupBo sourceObject) {

		AttributeLookup attributeLookup = new AttributeLookup();

		Mapper.genericMapper.map(sourceObject, attributeLookup);
		
		return attributeLookup;
	}

	public AttrLookupGroupBo map(AttrLookupGroup sourceObject) {

		
		AttrLookupGroupBo attrLookupGroupBo = new AttrLookupGroupBo();

		Mapper.genericMapper.map(sourceObject, attrLookupGroupBo);
		
		/*Set<AttributeLookupBo> attributeLookupBoSet = new HashSet<AttributeLookupBo>();
			
		recursiveMap.put(sourceObject.hashCode(), attrLookupGroupBo);
			
			for(AttributeLookup attributeLookup:sourceObject.getAttributeLookup()){
				if(null != attributeLookup && recursiveMap.containsKey(attributeLookup.hashCode())){
					if(null != recursiveMap.get(attributeLookup.hashCode()) && recursiveMap.get(attributeLookup.hashCode()) instanceof AttributeLookupBo)
						attributeLookupBoSet.add(((AttributeLookupBo)recursiveMap.get(attributeLookup.hashCode())));
				}else{
					attributeLookupBoSet.add(map(docListBo));
				}
			}
			employee.setDocuments(docList);
			document.setEmployee(employee);
		}*/
		
		return attrLookupGroupBo;
	}
	
	public AttrLookupGroup map(AttrLookupGroupBo sourceObject) {

		
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup();

		Mapper.genericMapper.map(sourceObject, attrLookupGroup);
		
		/*Set<AttributeLookupBo> attributeLookupBoSet = new HashSet<AttributeLookupBo>();
			
		recursiveMap.put(sourceObject.hashCode(), attrLookupGroupBo);
			
			for(AttributeLookup attributeLookup:sourceObject.getAttributeLookup()){
				if(null != attributeLookup && recursiveMap.containsKey(attributeLookup.hashCode())){
					if(null != recursiveMap.get(attributeLookup.hashCode()) && recursiveMap.get(attributeLookup.hashCode()) instanceof AttributeLookupBo)
						attributeLookupBoSet.add(((AttributeLookupBo)recursiveMap.get(attributeLookup.hashCode())));
				}else{
					attributeLookupBoSet.add(map(docListBo));
				}
			}
			employee.setDocuments(docList);
			document.setEmployee(employee);
		}*/
		
		return attrLookupGroup;
	}	
	
	public AttrLookupGroupBo map(AttrLookupGroupBo sourceObject, AttrLookupGroupBo destObject) {

		if(null == sourceObject || null == destObject){return null;}

		Mapper.genericMapper.map(sourceObject, destObject);
		return destObject;
	}
	
	public AttributeLookupBo map(AttributeLookupBo sourceObject, AttributeLookupBo destObject) {

		if(null == sourceObject || null == destObject){return null;}

		Mapper.genericMapper.map(sourceObject, destObject);
		return destObject;
	}
	
	@Override
	public Boolean mapUpdateContent(Object fromObject, Object toObject) {
		return null;
	}
	
	@Override
	public Object map(Object sourceObject) {
		return null;
	}
	
	@Override
	public Object map(Object fromObject, Object toObject) {
		return null;
	}
	
}
