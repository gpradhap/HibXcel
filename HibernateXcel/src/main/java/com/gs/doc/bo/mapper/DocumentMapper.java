package com.gs.doc.bo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.mapper.Mapper;
import com.gs.common.mapper.MapperHelper;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.bo.mapper.EmployeeMapper;
import com.gs.emp.vo.Employee;

public class DocumentMapper<T, Z> implements Mapper{

	static Logger log = Logger.getLogger(DocumentMapper.class);

	private static Boolean mapSuccessful = false;
	private static Object populatedObject = null;

	private ConcurrentHashMap<Integer, Object> recursiveMap = new ConcurrentHashMap<Integer, Object>();
	
	T destObject;
	
	@Override
	public T getMappedObject() {
		if(null != populatedObject){
			return (T)populatedObject;
		}
		return null;
	}
	
	@Override
	public Boolean isMapSuccess() {
		if(null != populatedObject){
			mapSuccessful = true;
		}
		return mapSuccessful;
	}

	
	public DocumentMapper() {
	}
	
	public DocumentBo map(Document document) {

		if(null == document){
			return null;
		}

		DocumentBo documentBo = new DocumentBo();
		
		Mapper.genericMapper.map(document,documentBo);
		
		/*MapperHelper.mapSimpleInstance(document,documentBo);

		Employee employee = document.getEmployee();
		
		if(null != employee){
			EmployeeBo employeeBo = new EmployeeBo();
			
			MapperHelper.mapSimpleInstance(employee,employeeBo);
			
			List<DocumentBo> docBoList = new ArrayList<DocumentBo>();
			
			for(Document docObj:employee.getDocuments()){
				if(null != docObj && docObj.hashCode() == document.hashCode()){
					docBoList.add(documentBo);
				}else{
					docBoList.add(map(docObj));
				}
			}
			employeeBo.setDocuments(docBoList);
			documentBo.setEmployee(employeeBo);
		}*/

		return documentBo;
	}	
	
	public Document map(DocumentBo documentBo) {

		if(null == documentBo){
			return null;
		}

		Document document = new Document();

		Mapper.genericMapper.map(documentBo, document);
		
		/*MapperHelper.mapSimpleInstance(documentBo, document);

		EmployeeBo employeeBo = documentBo.getEmployee();
		if(null != employeeBo){
			Employee employee = new Employee();
			
			MapperHelper.mapSimpleInstance(employeeBo, employee);
			
			List<Document> docList = new ArrayList<Document>();
			
			recursiveMap.put(documentBo.hashCode(), document);
			
			for(DocumentBo docListBo:employeeBo.getDocuments()){
				if(null != documentBo && recursiveMap.containsKey(docListBo.hashCode())){
					if(null != recursiveMap.get(docListBo.hashCode()) && recursiveMap.get(docListBo.hashCode()) instanceof Document)
						docList.add(((Document)recursiveMap.get(docListBo.hashCode())));
				}else{
					docList.add(map(docListBo));
				}
			}
			employee.setDocuments(docList);
			document.setEmployee(employee);
		}*/

		return document;
	}
	
	@Override
	public Object map(Object fromObject, Object toObject) {

		new MapperHelper().mapImpl(fromObject, toObject);
		
		System.out.println(" document mapper "+toObject);
		
		return toObject;
	}
	
	
	@Override
	public T map(Object sourceObject) {
		
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
		return (T)populatedObject;
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

	
	/*@Override
	public Boolean map(Object fromObject, BaseAbstractBo toObject) {

		new MapperHelper().mapImpl(fromObject, toObject);
		
		System.out.println(" document mapper "+toObject);
		
		return true;
	}*/
	
}
