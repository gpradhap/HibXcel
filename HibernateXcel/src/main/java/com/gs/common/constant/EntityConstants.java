package com.gs.common.constant;

public enum EntityConstants {
	TABLE(null) {
		@Override
		public String getStrValue() {
			return null;
		}
	},
	COLUMN(null) {
		@Override
		public String getStrValue() {
			return null;
		}
	},
	EXPIRE_DT(COLUMN) {
		public String getStrValue() {
			return "expireDate";
		};
	},
	NO_EXPIRE_DT_VALUE(COLUMN) {
		public String getStrValue() {
			return "01/01/2099";
		};
	},
	EXPIRE_DT_VALUE(COLUMN) {
		public String getStrValue() {
			return "01/01/1900";
		};
	};

	public abstract String getStrValue();

	private EntityConstants parent = null;

	public EntityConstants getParent(){
		return parent;
	}
	
	private EntityConstants(EntityConstants entityConstants) {
		this.parent = entityConstants;
	}

}
