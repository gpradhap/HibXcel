package com.gs.emp.bo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.mapper.Mapper;
import com.gs.common.mapper.MapperHelper;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.vo.Employee;

public class EmployeeMapper<T extends Object> implements Mapper {

	static Logger log = Logger.getLogger(EmployeeMapper.class);

	private static Boolean mapSuccessful = false;
	private static Object populatedObject = null;

	T destObject;

	@Override
	public Boolean isMapSuccess(){
		if(null != populatedObject){
			mapSuccessful = true;
		}
		return mapSuccessful;
	}

	@Override
	public T getMappedObject() {
		if(null != populatedObject){
			return (T) populatedObject;
		}
		return null;
	}

	/*public EmployeeBo getMappedEmployee() {
		return populatedEmp;
	}*/
	
	public Employee map(EmployeeBo employeeBo) {

		if(null == employeeBo){
			return null;
		}

		Employee employee = new Employee();

		Mapper.genericMapper.map(employeeBo,employee);
		
		/*MapperHelper.mapSimpleInstance(employeeBo, employee);

		List<DocumentBo> documentListBo = employeeBo.getDocuments();
		if(null != documentListBo){

			List<Document> documentList = new ArrayList<Document>();

			for(DocumentBo docListBo : documentListBo){
				 MapperHelper.mapSimpleInstance(docBo, employee); 
				Document document = new Document();
				MapperHelper.mapSimpleInstance(docListBo, document);
				
				if(null != docListBo.getEmployee()){
					if(null != employee && employee.hashCode() == docListBo.getEmployee().hashCode()){
						document.setEmployee(employee);
					}else{
						document.setEmployee(map(docListBo.getEmployee()));
					}
				}
				
				documentList.add(document);
			}
			employee.setDocuments(documentList);
		}*/

		return employee;
	}
	
	
	@Override
	public Object map(Object sourceObject) {
		
		try {
			new MapperHelper().mapImpl(sourceObject, destObject);

			if(null != destObject){
				populatedObject = (T)destObject;
			}

			if(log.isTraceEnabled()){
				log.trace("EmployeeMapper "+populatedObject);}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return populatedObject;
	}

	
	/*@Override
	public Boolean map(Object fromObject, BaseAbstractBo toObject) {
	new MapperHelper(this).mapImpl(fromObject, toObject);
		
		if(log.isTraceEnabled()){
		log.trace("EmployeeMapper "+toObject);}
		
		mapSuccessful = true;
		populatedObject = toObject;
		
		return true;
	}*/
	
	public EmployeeBo map(Employee empSrcObj) {
		if(null == empSrcObj){return null;}
		
		EmployeeBo employeeBo = new EmployeeBo();
		
		Mapper.genericMapper.map(empSrcObj, employeeBo);
		
		return employeeBo;
	}
	
	@Override
	public Object map(Object fromObject, Object toObject) {
		
		//new MapperHelper(this).mapImpl(fromObject, toObject);
		Mapper.genericMapper.map(fromObject, toObject);
		
		if(log.isTraceEnabled()){
		log.trace("EmployeeMapper "+toObject);}
		
		populatedObject = toObject; 
		
		return toObject;
	}

	@Override
	public Boolean mapUpdateContent(Object fromObject, Object toObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Mapper getMapperInstance(Class mapperClass) {
		return null;
	}

}
