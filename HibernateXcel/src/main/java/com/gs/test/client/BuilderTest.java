package com.gs.test.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gs.common.bo.AttributeValueBo;
import com.gs.doc.bo.DocumentAttributesBo;
import com.gs.doc.vo.Document;

public class BuilderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AttributeValueBo attributeValueBo1 = new AttributeValueBo("updatedBy", "pradhap");
		AttributeValueBo attributeValueBo2 = new AttributeValueBo("updatedDate", Calendar.getInstance().getTime());
		List<AttributeValueBo> attributeValueLst = new ArrayList<AttributeValueBo>();
		attributeValueLst.add(attributeValueBo1);
		attributeValueLst.add(attributeValueBo2);
		

		//Document doc = new Document.DocumentBuilder("SSN", new Date("06/01/2015"), "pradhap").populateAttributeValues(attributeValueLst).build();
		
		//Document doc2 = new Document.DocumentBuilder("SSN",Calendar.getInstance().getTime(),"Pradhap").setUpdatedBy("Pradhap").build();
		
		//System.out.println(doc.toString());

	}

}
