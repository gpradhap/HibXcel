package com.gs.common.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.gs.base.dao.exception.BaseAbstractDaoException;
import com.gs.base.service.BaseAbstractService;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.dao.AttrLookupGroupDao;
import com.gs.common.dao.AttributeLookupDao;
import com.gs.common.dao.exception.AttrLookupGroupDaoException;
import com.gs.common.mapper.Mapper;
import com.gs.common.service.exception.AttributeServiceException;
import com.gs.common.util.GenericsUtil;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.common.vo.AttributeLookup;
import com.gs.emp.bo.EmployeeAttributesBo;
import com.pg.annotat.utils.HibernateUtil;

public class AttributeLookupService extends BaseAbstractService{
	
	static Logger log = Logger.getLogger(AttributeLookupService.class);

	
	/***
	 * 
	 * @param attributeLookup
	 * @return
	 * @throws AttributeServiceException
	 */
	public AttributeLookup createAttributeLookup(AttributeLookup attributeLookup)
			throws AttributeServiceException {

		if(null == attributeLookup){
			return null;
		}

		AttributeLookupBo attributeLookupBo = Mapper.attributeMapper.map(attributeLookup);

		AttrLookupGroupBo attrLookupGroupBoIn = attributeLookupBo.getAttrLookupGroup();
		AttrLookupGroupBo attrLookupGroupBoRes = null;

		if(null != attrLookupGroupBoIn){

			attrLookupGroupBoRes = new AttrLookupGroupDao(getSessionFactory())
					.retrieveOne(attrLookupGroupBoIn);

			if(null != attrLookupGroupBoRes){

				populateAttrLookupGrpBo(attributeLookupBo, attrLookupGroupBoRes);

				try{
					attributeLookupBo = new AttributeLookupDao(getSessionFactory())
							.create(attributeLookupBo);

				}catch(Exception excep){
					log.error(" Exception in create AttributeLookup " + excep.getMessage());
					throw new AttributeServiceException("Failed to create attribute"
							+ excep.getMessage());
				}

			}else{
				try{

					populateUserDetails(attrLookupGroupBoIn, attributeLookup);
					
					attrLookupGroupBoRes = new AttrLookupGroupDao(getSessionFactory())
							.create(attrLookupGroupBoIn);
					if(null != attrLookupGroupBoRes
							&& null != attrLookupGroupBoRes.getAttributeLookup()
							&& attrLookupGroupBoRes.getAttributeLookup().size() > 0){
						for(AttributeLookupBo attributeLookupBoLo : attrLookupGroupBoRes
								.getAttributeLookup()){
							if(StringUtils.isNotBlank(attributeLookupBoLo.getAttribute())
									&& attributeLookupBoLo.getAttribute().equals(
											attributeLookupBo.getAttribute())){
								attributeLookupBo = attributeLookupBoLo;
							}
						}
					}

				}catch(Exception e){
					log.error("Could not able to create" + e.getMessage());
					throw new AttributeServiceException("Could not able to create" + e.getMessage());
				}
			}
		}

		attributeLookup = Mapper.attributeMapper.map(attributeLookupBo);

		return attributeLookup;
	}

	/***
	 * 
	 * @param attributeGroupName
	 * @return
	 */
	public Set<AttributeLookup> retrieveAll(String attributeGroupName) {

		if(StringUtils.isBlank(attributeGroupName)){
			return null;
		}

		AttributeLookupBo attributeLookupBo = new AttributeLookupBo();
		attributeLookupBo.setAttribute(attributeGroupName);

		Set<AttributeLookupBo> attrLookupGrpBoSet = new AttributeLookupDao(getSessionFactory())
				.retrieveSet(attributeLookupBo);

		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();

		if(null != attrLookupGrpBoSet && attrLookupGrpBoSet.size() > 0){

			for(AttributeLookupBo attrLookupBo : attrLookupGrpBoSet){
				if(null == attrLookupBo){
					continue;
				}

				System.out.println(" attr ?: " + attrLookupBo.getAttribute());

				AttributeLookup attributeLookupLo = Mapper.attributeMapper.map(attrLookupBo);

				if(null != attributeLookupLo){
					attributeLookupSet.add(attributeLookupLo);
				}
			}
		}

		return attributeLookupSet;
	}

	/***
	 * 
	 * @param attrLookupGroup
	 * @return
	 */
	public Set<AttributeLookup> retrieveAttributes(AttrLookupGroup attrLookupGroup) {
		if(null == attrLookupGroup){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGroup);

		AttrLookupGroupBo attrLookupGrpBo = new AttrLookupGroupDao(getSessionFactory())
				.retrieveOne(attrLookupGroupBo);

		Set<AttributeLookupBo> attrLookupsBo = null;

		if(null != attrLookupGrpBo && null != attrLookupGrpBo.getAttributeLookup()){
			attrLookupsBo = attrLookupGrpBo.getAttributeLookup();

			for(AttributeLookupBo attrLookupBo : attrLookupsBo){
				System.out.println(" attr ?: " + attrLookupBo.getAttribute());
				/*System.out.println(" EmpAttr ?: " + attrLookupBo.getEmployeeAttrBoSet());

				if(null != attrLookupBo.getEmployeeAttrBoSet()){
					for(EmployeeAttributesBo empAttrBo : attrLookupBo.getEmployeeAttrBoSet()){
						if(null != empAttrBo){
							System.out.println(" empAttrBo ?: " + empAttrBo.getAttributeDesc());
						}
					}
				}*/
			}
		}

		attrLookupGroup = Mapper.attributeMapper.map(attrLookupGrpBo);

		Set<AttributeLookup> attributeLookupSet = attrLookupGroup.getAttributeLookup();

		return attributeLookupSet;
	}
	
	/***
	 * 
	 * @param attrLookupGroup
	 * @return
	 * @throws AttributeServiceException 
	 */
	public Set<AttributeLookup> retrieveAttrWithEmps(AttrLookupGroup attrLookupGroup) throws AttributeServiceException {
		if(null == attrLookupGroup){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGroup);

		AttrLookupGroupBo attrLookupGrpBo = null;
		
		try{
			attrLookupGrpBo = new AttrLookupGroupDao(getSessionFactory())
					.retrieveAttrWithEmps(attrLookupGroupBo);
		}catch(AttrLookupGroupDaoException e){
			e.printStackTrace();
			throw new AttributeServiceException(e.getMessage());
		}

		Set<AttributeLookupBo> attrLookupsBo = null;

		if(null != attrLookupGrpBo && null != attrLookupGrpBo.getAttributeLookup()){
			attrLookupsBo = attrLookupGrpBo.getAttributeLookup();

			for(AttributeLookupBo attrLookupBo : attrLookupsBo){
				System.out.println(" attr ?: " + attrLookupBo.getAttribute());
				System.out.println(" EmpAttr ?: " + attrLookupBo.getEmpAttributeSet());

				if(null != attrLookupBo.getEmpAttributeSet()){
					for(EmployeeAttributesBo empAttrBo : attrLookupBo.getEmpAttributeSet()){
						if(null != empAttrBo){
							System.out.println(" empAttrBo ?: " + empAttrBo.getAttributeDesc());
						}
					}
				}

			}
		}

		attrLookupGroup = Mapper.attributeMapper.map(attrLookupGrpBo);

		Set<AttributeLookup> attributeLookupSet = attrLookupGroup.getAttributeLookup();

		return attributeLookupSet;
	}	

	/***
	 * 
	 * @param attributeLookup
	 * @return
	 */
	public Set<AttributeLookup> retrieveAttributes(AttributeLookup attributeLookup){
		if(null == attributeLookup){
			attributeLookup = new AttributeLookup();
		}
		
		AttributeLookupBo attributeLookupBo = Mapper.attributeMapper.map(attributeLookup);
		
		Set<AttributeLookupBo> attrLookupGrpBoSet =  new AttributeLookupDao(getSessionFactory()).retrieveSet(attributeLookupBo);
		
		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();
		
		if(null != attrLookupGrpBoSet && attrLookupGrpBoSet.size() > 0){

			for(AttributeLookupBo attrLookupBo  : attrLookupGrpBoSet){
				if(null == attrLookupBo){continue;}
				
				System.out.println(" attr ?: " + attrLookupBo.getAttribute());
				
				AttributeLookup attributeLookupLo = Mapper.attributeMapper.map(attrLookupBo);
				
				if(null != attributeLookupLo){
					attributeLookupSet.add(attributeLookupLo);
				}
			}
		}

		return attributeLookupSet;
	}

	/****
	 * 
	 * @param attributeLookup
	 * @return
	 * @throws AttributeServiceException
	 */
	public AttributeLookup updateAttributeLookup(AttributeLookup attributeLookup)
			throws AttributeServiceException {

		if(null == attributeLookup){
			return null;
		}

		AttributeLookupBo attributeLookupBoIn = Mapper.attributeMapper.map(attributeLookup);
		AttributeLookupBo attributeLookupBoRes = null; 
				
		AttrLookupGroupBo attrLookupGroupBoIn = attributeLookupBoIn.getAttrLookupGroup();
		AttrLookupGroupBo attrLookupGroupBoRes = null;

		//Update with Group
		if(null != attrLookupGroupBoIn){

			attrLookupGroupBoRes = new AttrLookupGroupDao(getSessionFactory())
					.retrieveOne(attrLookupGroupBoIn);

			if(null != attrLookupGroupBoRes && null != attrLookupGroupBoRes.getAttributeLookup()
					&& attrLookupGroupBoRes.getAttributeLookup().size() > 0){

				attributeLookupBoRes = attrLookupGroupBoRes.getAttributeLookup().iterator().next();

				if(null != attributeLookupBoRes){
					attributeLookupBoIn.setAttrLookupID(attributeLookupBoRes.getAttrLookupID());
					
					Mapper.attributeMapper.map(attributeLookupBoIn, attributeLookupBoRes);
				}
			}

			

		}else{ 		//Update only attribute
			try{
				attributeLookupBoRes = new AttributeLookupDao(getSessionFactory())
						.retrieveOne(attributeLookupBoIn);

				if(null != attributeLookupBoRes){
					attributeLookupBoIn.setAttrLookupID(attributeLookupBoRes.getAttrLookupID());

					Mapper.attributeMapper.map(attributeLookupBoIn, attributeLookupBoRes);
				}
			}catch(Exception e){
				log.error("Not find match record" + e.getMessage());
				throw new AttributeServiceException("Not find match record" + e.getMessage());
			}
		}

		if(null != attributeLookupBoRes && null != attributeLookupBoRes.getAttrLookupID()
				&& null != attributeLookupBoRes.getAttribute()){
			try{
				populateUserDetails(attributeLookupBoRes, attributeLookup);

				attributeLookupBoRes = new AttributeLookupDao(getSessionFactory())
						.update(attributeLookupBoRes);
			}catch(Exception e){
				log.error("Update Failed" + e.getMessage());
				throw new AttributeServiceException("Update Failed" + e.getMessage());
			}
		}else{
			throw new AttributeServiceException(
					"Not able to update; No match AttributeLookup found");
		}

		attributeLookup = Mapper.attributeMapper.map(attributeLookupBoRes);

		return attributeLookup;
	}
	
	
	/***
	 * Delete (update expireDate)
	 * 
	 * @param attributeLookup
	 * @return
	 * @throws AttributeServiceException
	 */
	public Boolean deleteAttributeLookup(AttributeLookup attributeLookup)
			throws AttributeServiceException {

		if(null == attributeLookup){
			return null;
		}

		AttributeLookupBo attributeLookupBoIn = Mapper.attributeMapper.map(attributeLookup);
		AttrLookupGroupBo attrLookupGroupBoIn = attributeLookupBoIn.getAttrLookupGroup();

		AttributeLookupBo attributeLookupBoRes = null; 
		AttrLookupGroupBo attrLookupGroupBoRes = null;
		Boolean deleteAttributeLookup = false;

		//if Group is inputted
		if(null != attrLookupGroupBoIn){

			attrLookupGroupBoRes = new AttrLookupGroupDao(getSessionFactory())
					.retrieveOne(attrLookupGroupBoIn);

			if(null != attrLookupGroupBoRes && null != attrLookupGroupBoRes.getAttributeLookup()
					&& attrLookupGroupBoRes.getAttributeLookup().size() > 0){
				attributeLookupBoRes = attrLookupGroupBoRes.getAttributeLookup().iterator().next();
			}
		}else{ 		//Update only attribute
			try{
				attributeLookupBoRes = new AttributeLookupDao(getSessionFactory())
						.retrieveOne(attributeLookupBoIn);

			}catch(Exception e){
				log.error("Not find match record" + e.getMessage());
				throw new AttributeServiceException("Not find match record" + e.getMessage());
			}
		}

		if(null != attributeLookupBoRes && null != attributeLookupBoRes.getAttrLookupID()
				&& null != attributeLookupBoRes.getAttribute()){
			try{
				populateUserDetails(attributeLookupBoRes, attributeLookup);

				deleteAttributeLookup = new AttributeLookupDao(getSessionFactory())
						.delete(attributeLookupBoRes);
			}catch(Exception e){
				log.error("Update Failed" + e.getMessage());
				throw new AttributeServiceException("Update Failed" + e.getMessage());
			}
		}else{
			throw new AttributeServiceException(
					"Not able to update; No match AttributeLookup found");
		}
		return deleteAttributeLookup;
	}
	
	
	/***
	 * Delete (update expireDate)
	 * 
	 * @param attributeLookup
	 * @return
	 * @throws AttributeServiceException
	 */
	public Boolean deleteAttributeLookupAll(AttributeLookup attributeLookup)
			throws AttributeServiceException {

		if(null == attributeLookup){
			return null;
		}

		AttributeLookupBo attributeLookupBoIn = Mapper.attributeMapper.map(attributeLookup);
		AttrLookupGroupBo attrLookupGroupBoIn = attributeLookupBoIn.getAttrLookupGroup();

		Set<AttributeLookupBo> attributeLookupBoRes = null; 
		AttrLookupGroupBo attrLookupGroupBoRes = null;
		Boolean deleteAttributeLookup = false;

		System.out.println("attributeLookupBoIn hashCode "+attributeLookupBoIn.hashCode());
		//if Group is inputted
		if(null != attrLookupGroupBoIn){

			attrLookupGroupBoRes = new AttrLookupGroupDao(getSessionFactory())
					.retrieveOne(attrLookupGroupBoIn);

			if(null != attrLookupGroupBoRes && null != attrLookupGroupBoRes.getAttributeLookup()
					&& attrLookupGroupBoRes.getAttributeLookup().size() > 0){
				attributeLookupBoRes = attrLookupGroupBoRes.getAttributeLookup();
			}
		}else if(attributeLookupBoIn.hashCode() != -1 || !GenericsUtil.isEmptySimplIns(attributeLookupBoIn)){ 		//Update only attribute
			try{
				attributeLookupBoRes = new AttributeLookupDao(getSessionFactory())
						.retrieveSet(attributeLookupBoIn);

			}catch(Exception e){
				log.error("Not find match record" + e.getMessage());
				throw new AttributeServiceException("Not find match record" + e.getMessage());
			}
		}else{
			throw new AttributeServiceException("Not find match record. No records deleted");
		}

		if(null != attributeLookupBoRes && attributeLookupBoRes.size() > 0){
			for(AttributeLookupBo attributeLookupBoResLo: attributeLookupBoRes){
				populateUserDetails(attributeLookupBoResLo, attributeLookup);
			}
			try{
				deleteAttributeLookup = new AttributeLookupDao(getSessionFactory())
						.deleteAll(attributeLookupBoRes);
			}catch(Exception e){
				log.error("Update Failed" + e.getMessage());
				throw new AttributeServiceException("Update Failed" + e.getMessage());
			}
		}else{
			throw new AttributeServiceException(
					"Not able to update; No match AttributeLookup found");
		}
		return deleteAttributeLookup;
	}	
	
	/***
	 * 
	 * @param attrLookupGroup
	 * @return
	 * @throws AttributeServiceException 
	 */
	public Set<AttributeLookup> retrieveDeletedAttributes(AttributeLookup attributeLookup) throws AttributeServiceException{
		if(null == attributeLookup){
			attributeLookup = new AttributeLookup();
		}
		
		AttributeLookupBo attributeLookupBo = Mapper.attributeMapper.map(attributeLookup);
		
		Set<AttributeLookupBo> attrLookupGrpBoSet = null;
		try{
		
			attrLookupGrpBoSet = new AttributeLookupDao(getSessionFactory()).retrieveDeletedSet(attributeLookupBo);

		}catch(BaseAbstractDaoException e){
			log.error("Failed to retrieveDeletedAttributes" + e.getMessage());
			e.printStackTrace();
			throw new AttributeServiceException("Failed to retrieveDeletedAttributes" + e.getMessage());
		}
		
		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();
		
		if(null != attrLookupGrpBoSet && attrLookupGrpBoSet.size() > 0){

			for(AttributeLookupBo attrLookupBo  : attrLookupGrpBoSet){
				if(null == attrLookupBo){continue;}
				
				System.out.println(" attr ?: " + attrLookupBo.getAttribute());
				
				AttributeLookup attributeLookupLo = Mapper.attributeMapper.map(attrLookupBo);
				
				if(null != attributeLookupLo){
					attributeLookupSet.add(attributeLookupLo);
				}
			}
		}

		return attributeLookupSet;
	}	
	
	/***
	 * 
	 * @param attributeLookupBo
	 * @param attrLookupGroupBo
	 */
	private void populateAttrLookupGrpBo(AttributeLookupBo attributeLookupBo,
			AttrLookupGroupBo attrLookupGroupBo) {
		
		Set<AttributeLookupBo> attributeLookupBoSet = new HashSet<AttributeLookupBo>();
		
		
		
		attributeLookupBoSet.add(attributeLookupBo);

		attrLookupGroupBo.setAttributeLookup(attributeLookupBoSet);
		attributeLookupBo.setAttrLookupGroup(attrLookupGroupBo);
	}

}
