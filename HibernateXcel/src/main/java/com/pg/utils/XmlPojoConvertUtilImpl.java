package com.pg.utils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlPojoConvertUtilImpl implements XmlPojoConvertUtil {

	private String fileWritePath;
	private String fileReadPath;
	private Object objSource;
	private Class pojoType;
	private Class srcType;	

	public XmlPojoConvertUtilImpl() {
	}
	
	public XmlPojoConvertUtilImpl(Class pojoType, Class srcType) {
		this.fileReadPath = XMLPOJO.READ_XML.PATH+srcType.getSimpleName()+".xml";
		this.fileWritePath = XMLPOJO.WRITE_XML.PATH+srcType.getSimpleName()+".xml";
		this.pojoType = pojoType;
		this.srcType = srcType;
	}

	/*public XmlPojoConvertUtilImpl(Object objSource,
			Class classType) {
		this.fileWritePath = XMLPOJO.WRITE_XML.PATH+classType.getSimpleName()+".xml";
		this.objSource = objSource;
		this.classType = classType;
	}*/

	/*public XmlPojoConvertUtilImpl(String filePath, Object objSource,
			Class classType) {
		this.filePath = filePath;
		this.objSource = objSource;
		this.classType = classType;
	}*/

	public boolean pojoToXmlConvert() {
		try {
			System.out.println("converting " + srcType.getSimpleName()
					+ " to xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(pojoType,srcType);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream fout = new FileOutputStream(fileWritePath);
			jaxbMarshaller.marshal(objSource, fout);

			System.out.println(" marshal successfull ");
			System.out.println(" xml file is created in followign location "
					+ "\n" + fileWritePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Object xmlToPojoConvert() {
		Object xmlToPojo = null;
		try {
			System.out.println("converting " + fileReadPath + " to pojo"
					+ srcType.getSimpleName());

			JAXBContext jaxbContext = JAXBContext.newInstance(pojoType,srcType);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			FileInputStream fis = new FileInputStream(fileReadPath);
			//BufferedWriter fwriter = new BufferedWriter(new FileWriter())	
			xmlToPojo = jaxbUnMarshaller.unmarshal(fis);

			System.out.println(" UN-marshal successfull ");
			System.out.println(" POJO is created" + xmlToPojo);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
		return xmlToPojo;
	}

	public String getFileWritePath() {
		return fileWritePath;
	}

	public void setFileWritePath(String fileWritePath) {
		this.fileWritePath = fileWritePath;
	}

	public String getFileReadPath() {
		return fileReadPath;
	}

	public void setFileReadPath(String fileReadPath) {
		this.fileReadPath = fileReadPath;
	}

	public Object getObjSource() {
		return objSource;
	}

	public void setObjSource(Object objSource) {
		this.objSource = objSource;
	}
}
