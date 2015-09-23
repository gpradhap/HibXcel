package com.gs.common.mapper;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.doc.vo.Document;
import com.gs.emp.vo.Employee;

public class MapperImpl implements Mapper{

	@Override
	public Mapper getMapperInstance(Class mapperClass) {
		if(null == mapperClass) return null;
		if(mapperClass.equals(Document.class)){
			return documentMapper;
		}else if(mapperClass.equals(Employee.class)){
			return employeeMapper;
		}
		
		return null;
	}
	
	@Override
	public Object map(Object sourceObj, Object destObj) {
		//new MapperHelper().mapImpl(sourceObj, destObj);
		MapperImplHelper.map(sourceObj, destObj);
		return destObj;
	}
	
	@Override
	public Boolean mapUpdateContent(Object sourceObj, Object destObj) {
		//new MapperHelper().mapUpdateContentImpl(sourceObj, destObj);
		MapperImplHelper.map(sourceObj, destObj);
		return true;
	}
	
	@Override
	public Object map(Object sourceObject) {
		return null;
	}
	
	@Override
	public Object getMappedObject() {
		return null;
	}
	@Override
	public Boolean isMapSuccess() {
		return null;
	}
	
	
	/*@Override
	public Boolean map(Object fromObject, BaseAbstractBo toObject) {
		return null;
	}*/

	public MapperImpl() {
	}
	
}
