package com.gs.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gs.base.dao.exception.BaseAbstractDaoException;
import com.gs.base.service.BaseAbstractService;
import com.gs.base.service.exception.BaseAbstractException;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.dao.AttrLookupGroupDao;
import com.gs.common.mapper.Mapper;
import com.gs.common.service.exception.AttributeServiceException;
import com.gs.common.vo.AttrLookupGroup;

public class AttrLookupGroupService extends BaseAbstractService {

	static Logger log = Logger.getLogger(AttrLookupGroupService.class);

	public Set<AttrLookupGroup> retrieveAll() {

		Set<AttrLookupGroupBo> attrLookupGrpBoSet = new AttrLookupGroupDao(getSessionFactory())
				.retrieveSet(new AttrLookupGroupBo());

		if(null == attrLookupGrpBoSet || attrLookupGrpBoSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved ");
			}
			return null;
		}

		Set<AttrLookupGroup> attrLookupGrpSet = new HashSet<AttrLookupGroup>();

		for(AttrLookupGroupBo attrLookupGrpBo : attrLookupGrpBoSet){
			if(null == attrLookupGrpBo){
				continue;
			}
			AttrLookupGroup attrLookupGroup = Mapper.attributeMapper.map(attrLookupGrpBo);
			if(null != attrLookupGroup){
				attrLookupGrpSet.add(attrLookupGroup);
			}
		}

		return attrLookupGrpSet;

	}

	public Set<AttrLookupGroup> retrieveAll(String attributeGroupName) {

		if(StringUtils.isNotBlank(attributeGroupName)){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = new AttrLookupGroupBo(attributeGroupName);

		Set<AttrLookupGroupBo> attrLookupGrpBoSet = new AttrLookupGroupDao(getSessionFactory())
				.retrieveSet(attrLookupGroupBo);

		if(null == attrLookupGrpBoSet || attrLookupGrpBoSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved for groupName " + attributeGroupName);
			}

			return null;
		}

		Set<AttrLookupGroup> attrLookupGrpSet = new HashSet<AttrLookupGroup>();

		for(AttrLookupGroupBo attrLookupGrpBo : attrLookupGrpBoSet){
			if(null == attrLookupGrpBo){
				continue;
			}
			AttrLookupGroup attrLookupGroup = Mapper.attributeMapper.map(attrLookupGrpBo);
			if(null != attrLookupGroup){
				attrLookupGrpSet.add(attrLookupGroup);
			}
		}

		return attrLookupGrpSet;
	}

	public Set<AttrLookupGroup> retrieveAll(AttrLookupGroup attrLookupGroup) {

		if(null == attrLookupGroup){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGroup);

		Set<AttrLookupGroupBo> attrLookupGrpBoSet = new AttrLookupGroupDao(getSessionFactory())
				.retrieveSet(attrLookupGroupBo);

		if(null == attrLookupGrpBoSet || attrLookupGrpBoSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved for " + attrLookupGroup.toString());
			}

			return null;
		}

		Set<AttrLookupGroup> attrLookupGrpSet = new HashSet<AttrLookupGroup>();

		for(AttrLookupGroupBo attrLookupGrpBo : attrLookupGrpBoSet){
			if(null == attrLookupGrpBo){
				continue;
			}
			AttrLookupGroup attrLookupGroupLo = Mapper.attributeMapper.map(attrLookupGrpBo);
			if(null != attrLookupGroupLo){
				attrLookupGrpSet.add(attrLookupGroupLo);
			}
		}

		return attrLookupGrpSet;
	}

	public AttrLookupGroup retrieveOne(String attributeGroupName) {

		if(StringUtils.isNotBlank(attributeGroupName)){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = new AttrLookupGroupBo(attributeGroupName);

		Set<AttrLookupGroupBo> attrLookupGrpBoSet = new AttrLookupGroupDao(getSessionFactory())
				.retrieveSet(attrLookupGroupBo);

		if(null == attrLookupGrpBoSet || attrLookupGrpBoSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved for groupName " + attributeGroupName);
			}

			return null;
		}

		Set<AttrLookupGroup> attrLookupGrpSet = new HashSet<AttrLookupGroup>();

		for(AttrLookupGroupBo attrLookupGrpBo : attrLookupGrpBoSet){
			if(null == attrLookupGrpBo){
				continue;
			}
			AttrLookupGroup attrLookupGroup = Mapper.attributeMapper.map(attrLookupGrpBo);
			if(null != attrLookupGroup){
				attrLookupGrpSet.add(attrLookupGroup);
			}
		}

		if(null != attrLookupGrpSet && attrLookupGrpSet.size() > 1){
			if(log.isDebugEnabled()){
				log.debug(" More than one records retrieved for groupName " + attributeGroupName);
			}
		}

		AttrLookupGroup attrLookupGroup = null;

		if(null != attrLookupGrpSet && attrLookupGrpSet.size() > 0){
			attrLookupGroup = attrLookupGrpSet.iterator().next();
		}

		return attrLookupGroup;
	}
	
	public AttrLookupGroup retrieveOne(Integer attributeGroupId) {

		if(null == attributeGroupId){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = new AttrLookupGroupBo(attributeGroupId);

		attrLookupGroupBo = new AttrLookupGroupDao(getSessionFactory())
				.retrieveOne(attrLookupGroupBo);

		if(null == attrLookupGroupBo ){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved for groupName " + attributeGroupId);
			}

			return null;
		}

		AttrLookupGroup attrLookupGroup = Mapper.attributeMapper.map(attrLookupGroupBo);

		return attrLookupGroup;
	}
	
	public AttrLookupGroup retrieveOne(AttrLookupGroup attrLookupGroup) {

		if(null == attrLookupGroup){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGroup);

		Set<AttrLookupGroupBo> attrLookupGrpBoSet = new AttrLookupGroupDao(getSessionFactory())
				.retrieveSet(attrLookupGroupBo);

		if(null == attrLookupGrpBoSet || attrLookupGrpBoSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved " + attrLookupGroup.toString());
			}

			return null;
		}

		Set<AttrLookupGroup> attrLookupGrpSet = new HashSet<AttrLookupGroup>();

		for(AttrLookupGroupBo attrLookupGrpBo : attrLookupGrpBoSet){
			if(null == attrLookupGrpBo){
				continue;
			}
			AttrLookupGroup attrLookupGroupLo = Mapper.attributeMapper.map(attrLookupGrpBo);
			if(null != attrLookupGroupLo){
				attrLookupGrpSet.add(attrLookupGroupLo);
			}
		}

		if(null != attrLookupGrpSet && attrLookupGrpSet.size() > 1){
			if(log.isDebugEnabled()){
				log.debug(" More than one records retrieved for " + attrLookupGroup.toString());
			}
		}

		if(null != attrLookupGrpSet && attrLookupGrpSet.size() > 0){
			attrLookupGroup = attrLookupGrpSet.iterator().next();
		}

		return attrLookupGroup;
	}

	/*
	 * CREATE
	 */
	public AttrLookupGroup createAttrLookupGrp(AttrLookupGroup attrLookupGroup) {

		if(null == attrLookupGroup){
			return null;
		}

		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGroup);

		// Create a record
		try{
			attrLookupGroupBo = new AttrLookupGroupDao(getSessionFactory()).create(attrLookupGroupBo);
		}catch(BaseAbstractDaoException e){
			e.printStackTrace();
		}

		if(null == attrLookupGroupBo){
			if(log.isDebugEnabled()){
				log.debug(" no record is Created " + attrLookupGroupBo.toString());
			}
			return null;
		}

		attrLookupGroup = Mapper.attributeMapper.map(attrLookupGroupBo);

		return attrLookupGroup;
	}

	/*
	 * Update
	 */
	public AttrLookupGroup updateAttrLookupGrp(AttrLookupGroup attrLookupGroupInParam)
			throws AttributeServiceException {

		if(null == attrLookupGroupInParam){
			return null;
		}

		//Map updated VO to BO
		AttrLookupGroupBo attrLookupGroupBoInput = Mapper.attributeMapper
				.map(attrLookupGroupInParam);

		// Retrieve records before delete
		AttrLookupGroupBo attrLookupGrpBoResult= null;
		try{
			attrLookupGrpBoResult = new AttrLookupGroupDao(getSessionFactory())
					.retrieveOne(attrLookupGroupBoInput);
		}catch(Exception e){
			log.error("Exception: " + e.getMessage() + ", Can not retrieve AttrLookupGroup for "
					+ attrLookupGroupInParam.toString());
		}

		if(null == attrLookupGrpBoResult){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved " + attrLookupGroupInParam.toString());
			}
			attrLookupGrpBoResult = attrLookupGroupBoInput;
			
			throw new AttributeServiceException(" no records retrieved ");
						
		}else{
			System.out.println("before content" + attrLookupGrpBoResult.toString());
			//Update required fields input to retrieved data
			Mapper.genericMapper.map(attrLookupGroupBoInput, attrLookupGrpBoResult);
			System.out.println("after content" + attrLookupGrpBoResult.toString());
		}

		
		/*if(null != attrLookupGroupBoInput){
			if(null != attrLookupGroupBoInput.getAttributeLookup()
					&& attrLookupGroupBoInput.getAttributeLookup().size() > 0){
				
				for(AttributeLookupBo aLookupBo : attrLookupGroupBoInput.getAttributeLookup()){

					boolean aLookupIsSame = false;
					
					AttributeLookupBo srcAttrLookupBo = aLookupBo;
					AttributeLookupBo destAttrLookupBo = null;
					
					if(null == aLookupBo){
						continue;
					}
					if(null != attrLookupGrpBoResult){
						if(null != attrLookupGrpBoResult.getAttributeLookup()
								&& attrLookupGrpBoResult.getAttributeLookup().size() > 0){
							for(AttributeLookupBo aLookupResultBo : attrLookupGrpBoResult
									.getAttributeLookup()){
								if(null == aLookupResultBo){
									continue;
								}
								if(StringUtils.isNotBlank(aLookupBo.getAttribute())
										&& aLookupBo.getAttribute().equals(
												aLookupResultBo.getAttribute())){
									aLookupIsSame = true;
									destAttrLookupBo = aLookupResultBo;
									break;
								}
							}
						}
					}
					
					if(aLookupIsSame){
						System.out.println("before content" + destAttrLookupBo.toString());
						destAttrLookupBo = Mapper.attributeMapper.map(srcAttrLookupBo, destAttrLookupBo);
						System.out.println("after content" + destAttrLookupBo.toString());
					}
					
				}
			}
		}*/
		// Populate updatedBy
		populateUserDetails(attrLookupGrpBoResult, attrLookupGroupInParam);

		//DAO call to update
		try{
			attrLookupGrpBoResult = new AttrLookupGroupDao(getSessionFactory())
					.update(attrLookupGrpBoResult);
		}catch(Exception e){
			log.error("Update fails "+e.getMessage());
		}

		//Map updated BO to VO
		attrLookupGroupInParam = Mapper.attributeMapper
				.map(attrLookupGrpBoResult);
		
		if(log.isDebugEnabled()){
			log.debug("Following record is deleted? >" + attrLookupGroupInParam.toString());
		}

		return attrLookupGroupInParam;
	}

	/*
	 * Delete
	 */
	public Boolean deleteAttrLookupGrp(AttrLookupGroup attrLookupGroupInParam)
			throws BaseAbstractException {

		if(null == attrLookupGroupInParam){
			return null;
		}

		Boolean deleteAttrLookupGrpFlag = false;

		AttrLookupGroupBo attrLookupGroupBoInput = Mapper.attributeMapper
				.map(attrLookupGroupInParam);

		// Retrieve records before delete
		Set<AttrLookupGroupBo> attrLookupGrpBoResultSet = null;
		try{
			attrLookupGrpBoResultSet = new AttrLookupGroupDao(getSessionFactory())
					.retrieveSet(attrLookupGroupBoInput);
		}catch(Exception e){
			log.error("Can not retrieve AttrLookupGroup for " + attrLookupGroupInParam.toString());
		}

		if(null == attrLookupGrpBoResultSet || attrLookupGrpBoResultSet.size() == 0){
			if(log.isDebugEnabled()){
				log.debug(" no records retrieved " + attrLookupGroupInParam.toString());
			}
			return false;
		}

		if(attrLookupGrpBoResultSet.size() > 1){
			throw new BaseAbstractException() {
				@Override
				public void setErrorMsg(String errorMsg) {
					super.setErrorMsg("Expected AttrLookupGroup one matching object but returns more than one.");
				}
			};
		}

		AttrLookupGroupBo deleteAttrLookupGrpBoResult = null;

		for(AttrLookupGroupBo attrLookupGrpBoResult : attrLookupGrpBoResultSet){
			if(null != attrLookupGrpBoResult){
				deleteAttrLookupGrpBoResult = attrLookupGrpBoResult;
				break;
			}
		}

		// Populate updatedBy
		populateUserDetails(deleteAttrLookupGrpBoResult, attrLookupGroupInParam);

		deleteAttrLookupGrpFlag = new AttrLookupGroupDao(getSessionFactory())
				.delete(deleteAttrLookupGrpBoResult);

		if(log.isDebugEnabled()){
			log.debug("Following record is deleted? >" + deleteAttrLookupGrpFlag + "<"
					+ attrLookupGroupInParam.toString());
		}

		return deleteAttrLookupGrpFlag;
	}
}
