package com.gs.common.helper;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gs.common.bo.AttributeValueBo;

public abstract class AttributeBuilderHelper {

	public abstract AttributeBuilderHelper populateAttributeValues(List<AttributeValueBo> attrValueList);
	
	public Object addAttributeValue(AttributeValueBo attrValue) {
		Method[] methods = this.getClass().getMethods();
		StringBuffer buffer = new StringBuffer();
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
