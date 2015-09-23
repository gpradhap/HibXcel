package com.gs.common.builder;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gs.common.bo.AttributeValueBo;
import com.gs.doc.vo.Document;

public abstract class AttributeBuilder {

	public abstract AttributeBuilder populateAttributeValues(List<AttributeValueBo> attrValueList);
	
	public abstract Object build();
	
	public Object addAttributeValue(AttributeValueBo attrValue) {
		Method[] methods = this.getClass().getMethods();
		try {
			for (int count = 0; count < methods.length; count++) {

				Method method = methods[count];
				String methodName = method.getName();
				String attrCode = attrValue.getCode();

				if (StringUtils.isNotBlank(methodName)
						&& StringUtils.isNotBlank(attrCode)
						&& methodName.toLowerCase().contains(
								attrCode.toLowerCase())) {
					method.invoke(this, attrValue.getValue());
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

}
