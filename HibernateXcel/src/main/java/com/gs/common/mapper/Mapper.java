package com.gs.common.mapper;

import org.apache.log4j.Logger;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.util.FactoryHelper;
import com.gs.doc.bo.mapper.DocumentMapper;
import com.gs.emp.bo.mapper.EmployeeMapper;

public interface Mapper{
	
	public static DocumentMapper documentMapper= FactoryHelper.getInstance(DocumentMapper.class);
	public static EmployeeMapper employeeMapper= FactoryHelper.getInstance(EmployeeMapper.class);
	public static AttributeMapper attributeMapper= FactoryHelper.getInstance(AttributeMapper.class);
	//public static AMapper employeeMapper= FactoryHelper.getInstance(EmployeeMapper.class);
	
	public static Mapper genericMapper= FactoryHelper.getInstance(MapperImpl.class);
	
	public Boolean isMapSuccess();
	public Object getMappedObject();
	
	public Object map(Object fromObject, Object toObject);
	public Object map(Object sourceObject);
	public Mapper getMapperInstance(Class mapperClass);
	
	public Boolean mapUpdateContent(Object fromObject, Object toObject);
}
