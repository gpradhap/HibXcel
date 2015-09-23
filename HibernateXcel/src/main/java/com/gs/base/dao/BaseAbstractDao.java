package com.gs.base.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.base.dao.exception.BaseAbstractDaoException;
import com.gs.common.command.FieldTypeCmd;
import com.gs.common.constant.EntityConstants;
import com.gs.common.util.GenericsUtil;

public abstract class BaseAbstractDao<T extends BaseAbstractBo> {

	private static final Logger log = Logger.getLogger(BaseAbstractDao.class);

	private final SessionFactory sessionFactory;

	private T defaultBo;

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public BaseAbstractDao() {
		this.sessionFactory = getSessionFactory();
	}

	public BaseAbstractDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession(SessionFactory sessionFactory) {

		if(null == sessionFactory && null == this.sessionFactory){
			return null;
		}

		if(null == sessionFactory && null != this.sessionFactory){
			sessionFactory = this.sessionFactory;
		}

		Session session = null;
		if(sessionFactory.openSession().isOpen()){
			session = sessionFactory.getCurrentSession();
		}else{
			session = sessionFactory.openSession();
		}
		return session;
	}

	public Session getSession() {

		if(null == sessionFactory){
			return null;
		}

		Session session = null;
		if(sessionFactory.openSession().isOpen()){
			session = sessionFactory.getCurrentSession();
		}else{
			session = sessionFactory.openSession();
		}
		return session;
	}

	public T create(T obj) throws BaseAbstractDaoException {

		if(null == obj || sessionFactory == null){
			return null;
		}

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try{
			
			obj = create(obj,session);
			
			/*
			populateCreateDefaults(obj);

			session.saveOrUpdate(obj);*/

			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			throw new BaseAbstractDaoException(e.getMessage());
		}

		return obj;

	}
	
	public T create(T obj,Session session) throws BaseAbstractDaoException {

		if(null == obj || session == null){
			return null;
		}

		try{
			populateCreateDefaults(obj);
			session.saveOrUpdate(obj);
		}catch(Exception e){
			e.printStackTrace();
			throw new BaseAbstractDaoException(e.getMessage());
		}

		return obj;

	}

	public Boolean deleteAll(Set<T> obj) throws BaseAbstractDaoException  {

		if(null == obj || sessionFactory == null || obj.size() ==0){
			throw new BaseAbstractDaoException("Invalid Input");
		}

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try{

			for(T tObjLo: obj){
				populateDeleteDefaults(tObjLo);
				session.update(tObjLo);
				
				if(log.isTraceEnabled()){
					log.trace("Deleted Success for Obj "+tObjLo.toString());
				}
			}
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			throw new BaseAbstractDaoException("DeleteAll failed because of: "+e.getStackTrace());
		}
		return true;
	}
	
	public Boolean delete(T obj) {

		if(null == obj || sessionFactory == null){
			return null;
		}

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try{

			populateDeleteDefaults(obj);

			session.update(obj);

			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}

		return true;

	}

	public T update(T obj) throws Exception {

		if(null == obj || sessionFactory == null){
			return null;
		}

		// Session session = sessionFactory.openSession();
		Session session = getSession();
		session.beginTransaction();

		try{

			populateUpdateDefaults(obj);

			session.update(obj);

			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			log.error("Update Fails " + e.getMessage());
			throw e;
		}

		return obj;

	}

	public Set<T> retrieveSet(T obj, Session session) throws BaseAbstractDaoException{
		if(null == obj || null == session){
			return null;
		}
		try{
			List<T> listT = retrieveList(obj,session);
			return new HashSet<T>(listT);
		}catch(Exception e){
			log.error(e.getStackTrace());
			throw new BaseAbstractDaoException(e.getMessage()); 
		}
	}
	
	public List<T> retrieveList(T obj, Session session) {

		if(null == obj || null == session){
			return null;
		}
		
		Criteria criteria = createCriteria(obj, session);

		List listObjs = criteria.list();

		if(log.isDebugEnabled()){
			log.debug("after criteria executed " + listObjs + " size of the list is?: "
					+ (null != listObjs ? listObjs.size() : 0));
		}

		return listObjs;
	}
	
	public Set<T> retrieveSet(T obj) {
		try{
			List<T> listT = retrieveList(obj);
			return new HashSet<T>(listT);
		}catch(Exception e){
			log.error(e.getStackTrace());
			return null;
		}
	}

	// public abstract T retrieveOne(T obj);
	public List<T> retrieveList(T obj) {

		List<T> retrieveList = null;

		SessionFactory sessionFactory = getSessionFactory();

		if(null == sessionFactory){
				log.error("sessionFactory is required. sessionFactory?: " + sessionFactory);
				return null;
		}

		Session session = sessionFactory.openSession();

		if(obj == null){
			/*
			 * Query query = session.createQuery("from " + defaultBo); List
			 * queryListObj = query.list();
			 * 
			 * if(null != queryListObj && queryListObj instanceof List){
			 * session.close(); return queryListObj; }
			 */

			return null;
		}

		Criteria criteria = createCriteria(obj, session);

		List listObjs = criteria.list();

		if(log.isDebugEnabled()){
			log.debug("after criteria executed " + listObjs + " size of the list is?: "
					+ (null != listObjs ? listObjs.size() : 0));
		}

		session.close();

		return listObjs;
	}

	private Criteria createCriteria(T obj, Session session) {
		Criteria criteria = session.createCriteria(obj.getClass());

		// Field[] fields = obj.getClass().getDeclaredFields();
		// Method[] methods = obj.getClass().getDeclaredMethods();

		Field[] fields = GenericsUtil.getAllFields(obj);

		if(log.isTraceEnabled()){
			log.trace("DeclaredFields?: " + fields);
			// log.trace("DeclaredMethods?: " + methods);
		}

		try{
			for(Field inputField : fields){

				if(null == inputField){
					continue;
				}

				String fieldName = inputField.getName();

				if(log.isTraceEnabled()){
					log.trace("inputField?: " + inputField);
					log.trace("fieldName?: " + fieldName);
				}

				String methodName = FieldTypeCmd.createGetMethodName(inputField);

				if(StringUtils.isBlank(methodName)){
					continue;
				}

				Object getMethodVal = null;

				try{
					Method inputMethod = obj.getClass().getMethod(methodName);

					getMethodVal = inputMethod.invoke(obj);

					if(log.isTraceEnabled()){
						log.trace("getMethodVal?: " + getMethodVal);
					}

					// add restrictions to criteria
					addRestrictions(criteria, inputField, fieldName, getMethodVal);

				}catch(IllegalArgumentException e){
					log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
				}catch(IllegalAccessException e){
					log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
				}catch(InvocationTargetException e){
					log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
				}catch(Exception e){
					log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
				}
			}
		}catch(Exception e){
			log.error("Exception " + e.getMessage());
		}
		return criteria;
	}

	public Set<T> retrieveDeletedSet(T obj) throws BaseAbstractDaoException {
		try{
			List<T> listT = retrieveDeletedList(obj);
			return new HashSet<T>(listT);
		}catch(Exception e){
			throw new BaseAbstractDaoException(e.getMessage());
		}
	}
	public List<T> retrieveDeletedList(T obj)  throws BaseAbstractDaoException {

		List<T> retrieveList = null;

		SessionFactory sessionFactory = getSessionFactory();

		if(null == sessionFactory){
			if(log.isDebugEnabled()){
				log.debug("sessionFactory is required. sessionFactory?: " + sessionFactory);
				return null;
			}
		}

		Session session = sessionFactory.openSession();

		if(obj == null){
			return null;
		}

		Criteria criteria = session.createCriteria(obj.getClass());

		Field[] fields = GenericsUtil.getAllFields(obj);

		if(log.isTraceEnabled()){
			log.trace("DeclaredFields?: " + fields);
		}

		try{
			for(Field inputField : fields){

				if(null == inputField){
					continue;
				}

				String fieldName = inputField.getName();

				if(log.isTraceEnabled()){
					log.trace("inputField?: " + inputField);
					log.trace("fieldName?: " + fieldName);
				}

				String methodName = FieldTypeCmd.createGetMethodName(inputField);

				if(StringUtils.isBlank(methodName)){
					continue;
				}

				Object getMethodVal = null;

				try{
					Method inputMethod = obj.getClass().getMethod(methodName);

					getMethodVal = inputMethod.invoke(obj);

					if(log.isTraceEnabled()){
						log.trace("getMethodVal?: " + getMethodVal);
					}

					// Only Delete/In-Active records
					if(EntityConstants.COLUMN.EXPIRE_DT.getStrValue().equalsIgnoreCase(fieldName)
							&& null == getMethodVal){
						criteria.add(Restrictions.lt(EntityConstants.COLUMN.EXPIRE_DT.getStrValue(), Calendar
								.getInstance().getTime()));
					}
					
					// add restrictions to criteria
					addRestrictions(criteria, fieldName, getMethodVal);

				}catch(Exception e){
					log.error("Exception invoke method " + methodName + " msg:" + e.getMessage());
				}
			}
		}catch(Exception e){
			log.error("Exception " + e.getMessage());
		}

		List listObjs = criteria.list();

		if(log.isDebugEnabled()){
			log.debug("after criteria executed " + listObjs + " size of the list is?: "
					+ (null != listObjs ? listObjs.size() : 0));
		}

		session.close();

		return listObjs;
	}
	
	private void addRestrictions(Criteria criteria, String fieldName,
			Object getMethodVal) {

		if(null != getMethodVal && GenericsUtil.isSimpleInstanceof(getMethodVal)){

			criteria.add(Restrictions.eq(fieldName, getMethodVal));

			if(log.isTraceEnabled()){
				log.trace("Restrictions added for field?: " + fieldName);
			}
		}else{
			if(log.isTraceEnabled()){
				log.trace("Not a Simple Instance?: " + getMethodVal);
			}
		}
	}
	
	private void addRestrictions(Criteria criteria, Field inputField, String fieldName,
			Object getMethodVal) {

		if(EntityConstants.COLUMN.EXPIRE_DT.getStrValue().equalsIgnoreCase(fieldName)
				&& null == getMethodVal){
			// Only active records
			criteria.add(Restrictions.ge(EntityConstants.COLUMN.EXPIRE_DT.getStrValue(), Calendar
					.getInstance().getTime()));
		}

		if(null != getMethodVal && GenericsUtil.isSimpleInstanceof(getMethodVal)){

			criteria.add(Restrictions.eq(fieldName, getMethodVal));

			if(log.isTraceEnabled()){
				log.trace("Restrictions added for field?: " + inputField);
			}
		}else{
			if(log.isTraceEnabled()){
				log.trace("Not a Simple Instance?: " + getMethodVal);
			}
		}
	}

	public abstract List<T> createList(T obj);

	public T retrieveOne(T obj) {
		try{
			List<T> listT = retrieveList(obj);

			if(null != listT && listT.size() > 1){
				log.warn("retrieveOne return more than one record but returns only one");
				return listT.get(0);
			}else if(null != listT && listT.size() == 1){
				return listT.get(0);
			}else{
				return null;
			}

		}catch(Exception e){
			log.error(e.getStackTrace());
			return null;
		}
	}

	public T retrieveOne(T obj, Session session) {
		
		if(null == obj || null == session){
			return null;
		}
		
		try{
			List<T> listT = retrieveList(obj, session);

			if(null != listT && listT.size() > 1){
				log.warn("retrieveOne return more than one record but returns only one");
				return listT.get(0);
			}else if(null != listT && listT.size() == 1){
				return listT.get(0);
			}else{
				return null;
			}

		}catch(Exception e){
			log.error(e.getStackTrace());
			return null;
		}
	}

	public T getOneRecord(Session session,Class clazz, Integer id) {
		
		if(null == clazz || null == id || null == session){
			return null;
		}
		T obj;
		try{
			obj = (T)session.get(clazz,id);
		}catch(Exception e){
			log.error(e.getStackTrace());
			return null;
		}
		return obj;
	}


	/*
	 * public T retrieveOne(T obj) { if(obj == null){ return obj; }
	 * 
	 * SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * if(null == sessionFactory){ if(log.isDebugEnabled()){
	 * log.debug("sessionFactory is required. sessionFactory?: " +
	 * sessionFactory); return null; } } Session session = getSession();
	 * 
	 * session.beginTransaction();
	 * 
	 * Criteria criteria = session.createCriteria(obj.getClass());
	 * 
	 * // Field[] fields = obj.getClass().getDeclaredFields(); // Method[]
	 * methods = obj.getClass().getDeclaredMethods();
	 * 
	 * Field[] fields = GenericsUtil.getAllFields(obj);
	 * 
	 * if(log.isTraceEnabled()){ log.trace("DeclaredFields?: " + fields); //
	 * log.trace("DeclaredMethods?: " + methods); }
	 * 
	 * List listObjs = null; try{ for(Field inputField : fields){
	 * 
	 * if(null == inputField) continue;
	 * 
	 * String fieldName = inputField.getName();
	 * 
	 * if(log.isTraceEnabled()){ log.trace("inputField?: " + inputField);
	 * log.trace("fieldName?: " + fieldName); }
	 * 
	 * String methodName = FieldTypeCmd.createGetMethodName(inputField);
	 * 
	 * if(StringUtils.isBlank(methodName)){continue;}
	 * 
	 * Object getMethodVal = null;
	 * 
	 * try{ Method inputMethod = obj.getClass().getMethod(methodName);
	 * 
	 * getMethodVal = inputMethod.invoke(obj);
	 * 
	 * if(log.isTraceEnabled()){ log.trace("getMethodVal?: " + getMethodVal); }
	 * 
	 * if(null != getMethodVal &&
	 * GenericsUtil.isSimpleInstanceof(getMethodVal)){
	 * 
	 * criteria.add(Restrictions.eq(fieldName, getMethodVal));
	 * 
	 * if(log.isTraceEnabled()){ log.trace("Restrictions added for field?: " +
	 * inputField); } }else{ if(log.isTraceEnabled()){
	 * log.trace("Not a Simple Instance?: " + getMethodVal); } }
	 * 
	 * }catch(IllegalArgumentException e){ log.error("Exception invoke method "
	 * + methodName + " msg:" + e.getMessage()); }catch(IllegalAccessException
	 * e){ log.error("Exception invoke method " + methodName + " msg:" +
	 * e.getMessage()); }catch(InvocationTargetException e){
	 * log.error("Exception invoke method " + methodName + " msg:" +
	 * e.getMessage()); }catch(Exception e){
	 * log.error("Exception invoke method " + methodName + " msg:" +
	 * e.getMessage()); } }
	 * 
	 * //Rertrieve list of objects listObjs = criteria.list();
	 * 
	 * session.getTransaction().commit(); //session.close() -- no need to close
	 * the session, because currentSession automatically gets closed after
	 * transaction is complete.
	 * 
	 * }catch(Exception e){ log.error("Exception " + e.getMessage());
	 * session.getTransaction().rollback(); }
	 * 
	 * 
	 * 
	 * 
	 * if(log.isDebugEnabled()){ log.debug("after criteria executed " + listObjs
	 * + " size of the list is?: " + (null != listObjs ? listObjs.size() : 0));
	 * }
	 * 
	 * if(null != listObjs && listObjs.size() > 0 && null != listObjs.get(0)){
	 * return (T) listObjs.get(0); }
	 * 
	 * return obj; }
	 */

	private void populateCreateDefaults(T tObj) {
		Set<Object> processedTObj = new HashSet<Object>();
		
		String updateBy = "N/A";
		
		if(StringUtils.isNotBlank(tObj.getUpdateBy())){
			updateBy = tObj.getUpdateBy();
		}
		
		populateCreateDefaultsRoutine(tObj,processedTObj,updateBy);
	}

	private void populateCreateDefaultsRoutine(T tObj, Set processedTObj, String updatedBy) {
		if(null == tObj.getExpireDate()){
			try{
				Date expireDate = new SimpleDateFormat("MM/dd/yyyy")
						.parse(EntityConstants.COLUMN.NO_EXPIRE_DT_VALUE.getStrValue());
				tObj.setExpireDate(expireDate);
			}catch(ParseException e){
				log.error("Date Parsing error " + e.getMessage());
			}
		}

		tObj.setUpdateDate(Calendar.getInstance().getTime());

		if(StringUtils.isNotBlank(updatedBy)){
			tObj.setUpdateBy(updatedBy);
		}
		
		processedTObj.add(tObj.hashCode());
		
		Field[] fields = GenericsUtil.getAllFields(tObj.getClass());
		
		for(Field field : fields){
			if(GenericsUtil.isComplexInstance(field.getType())){
				try{
					Object tObjLo = GenericsUtil.invokeGetMethod(tObj, field.getName());
					if(null != tObjLo && !processedTObj.contains(tObjLo.hashCode())){
						if(tObjLo instanceof BaseAbstractBo){
							populateCreateDefaultsRoutine((T) tObjLo,processedTObj,updatedBy);// Recurring call
						}
					}
				}catch(Exception e){
					log.warn("populateCreateDefauls fails " + e.getMessage());
					continue;
				}
			}else if(GenericsUtil.isCollectionInstance(field.getType())){
				try{
					Object tObjLo = GenericsUtil.invokeGetMethod(tObj, field.getName());

					if(null != tObjLo){
						Collection<BaseAbstractBo> tObjCol = null;
						if(tObjLo instanceof Set){
							tObjCol = (Set) tObjLo;
						}else if(tObjLo instanceof List){
							tObjCol = (List) tObjLo;
						}

						if(null != tObjCol && tObjCol.size() > 0){
							for(Object baseBo : tObjCol){
								populateCreateDefaultsRoutine((T) baseBo,processedTObj,updatedBy);// Recurring call
							}
						}

					}

				}catch(Exception e){
					log.warn("populateCreateDefauls fails " + e.getMessage());
					continue;
				}
			}
		}		
	}

	private void populateUpdateDefaults(T tObj) {

		tObj.setUpdateDate(Calendar.getInstance().getTime());

		if(tObj.getExpireDate() == null){
			try{
				Date expireDate = new SimpleDateFormat("MM/dd/yyyy")
						.parse(EntityConstants.COLUMN.NO_EXPIRE_DT_VALUE.getStrValue());
				tObj.setExpireDate(expireDate);
			}catch(ParseException e){
				log.error("Date Parsing error " + e.getMessage());
			}
		}
		
		if(tObj.getUpdateBy() == null){
			tObj.setUpdateBy("N/A");
		}
	}

	private void populateDeleteDefaults(T tObj) {
		try{
			Date expireDate = new SimpleDateFormat("MM/dd/yyyy")
					.parse(EntityConstants.COLUMN.EXPIRE_DT_VALUE.getStrValue());
			tObj.setExpireDate(expireDate);
		}catch(ParseException e){
			log.error("Date Parsing error " + e.getMessage());
		}

		tObj.setUpdateDate(Calendar.getInstance().getTime());

		if(tObj.getUpdateBy() == null){
			tObj.setUpdateBy("N/A");
		}
	}
}