package com.pg.utils;

public interface XmlPojoConvertUtil {
	public boolean pojoToXmlConvert();
	public Object xmlToPojoConvert();
	
	enum XMLPOJO{
		WRITE_XML("C://Pradhap//Tech_//General_Tech_//Proj Mini//HibernateXcel//src//main//resources//WritePojoToXml//"),
		READ_XML("C://Pradhap//Tech_//General_Tech_//Proj Mini//HibernateXcel//src//main//resources//ReadXmlToPojo//");
		
		String PATH = null;
		XMLPOJO(String PATH){
			this.PATH =PATH;
		}
	}
}
