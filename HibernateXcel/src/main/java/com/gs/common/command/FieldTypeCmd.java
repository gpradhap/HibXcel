package com.gs.common.command;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.gs.common.util.StringUtil;

public enum FieldTypeCmd {

	GET {

		boolean runCondition(String strParam) {
				return true;
		}
		
		boolean runCondition(Field fieldParam) {
			if (null != fieldParam && !fieldParam.getType().isPrimitive()) {
				return true;
			} else {
				return false;
			}
		}

		String getFieldName(String fieldName) {
			return StringUtil.formFieldToMethodName(fieldName, "get");
		}
		
		String setFieldName(String fieldName) {
			return StringUtil.formFieldToMethodName(fieldName, "set");
		}

	},
	IS {
		boolean runCondition(String strParam) {
			return false;
		}

		boolean runCondition(Field fieldParam) {
			if (null != fieldParam && fieldParam.getType().isPrimitive()
					&& "boolean".equals(fieldParam.getType().toString())) {
				return true;
			} else {
				return false;
			}
		}

		String getFieldName(String fieldName) {
			return StringUtil.formFieldToMethodName(fieldName, "is");
		}
		
		String setFieldName(String fieldName) {
			return StringUtil.formFieldToMethodName(fieldName, "set");
		}

	};

	abstract boolean runCondition(Field fieldParam);
	abstract boolean runCondition(String strParam);

	abstract String getFieldName(String fieldName);
	abstract String setFieldName(String fieldName);

	public static String createGetMethodName(String fieldName) {
		if (StringUtils.isBlank(fieldName)){return null; }
		
		for (FieldTypeCmd fieldTypeCmd : FieldTypeCmd.values()) {
			if (fieldTypeCmd.runCondition(fieldName)) {
				return fieldTypeCmd.getFieldName(fieldName);
			}
		}
		return null;
	}

	
	public static String createGetMethodName(Field field) {
		if (null == field)
			return null;
		for (FieldTypeCmd fieldTypeCmd : FieldTypeCmd.values()) {
			if (fieldTypeCmd.runCondition(field)) {
				return fieldTypeCmd.getFieldName(field.getName());
			}
		}
		return null;
	}

	public static String createSetMethodName(Field field) {
		if (null == field)
			return null;
		for (FieldTypeCmd fieldTypeCmd : FieldTypeCmd.values()) {
			if (fieldTypeCmd.runCondition(field)) {
				return fieldTypeCmd.setFieldName(field.getName());
			}
		}
		return null;
	}
	/*
	 * Field field = null; private FieldTypeCmd(Field field) { this.field =
	 * field; }
	 */
}
