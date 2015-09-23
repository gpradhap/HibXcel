package com.gs.base.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.base.vo.BaseAbstractVo;
import com.gs.base.vo.User;
import com.pg.annotat.utils.HibernateUtil;

public class BaseAbstractService {

	static Logger log = Logger.getLogger(BaseAbstractService.class);
	private static SessionFactory sessionFactory = null;

	static{
		try{
			sessionFactory = HibernateUtil.getSessionFactory();
		}catch(Exception e){
			log.error("could not get SessionFactory " + e.getMessage());
		}
	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/*protected void populateUserDetails(BaseAbstractBo abstractBo, User userInput) {
		if(null == abstractBo || null ==userInput){
			return;
		}
		if(null != abstractBo && StringUtils.isNotBlank(userInput.getUserName())){
			abstractBo.setUpdateBy(userInput.getUserName());
		}
	}*/
	protected void populateUserDetails(BaseAbstractBo abstractBo, BaseAbstractVo baseAbstractVo) {
		if(null == abstractBo || null ==baseAbstractVo){
			return;
		}
		if(null != abstractBo && StringUtils.isNotBlank(baseAbstractVo.getUpdateBy())){
			abstractBo.setUpdateBy(baseAbstractVo.getUpdateBy());
		}
	}
}
