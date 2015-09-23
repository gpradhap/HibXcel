package com.gs.common.service;

import com.gs.base.vo.User;
import com.gs.doc.vo.Document;

public interface PrivilegeService {
	
	public Boolean isViewable(Document document);
	public Boolean isDeleteable(Document document);
	public Boolean isUpdateable(Document document);
	
	public Boolean isViewable(User user);
	public Boolean isDeleteable(User user);
	public Boolean isUpdateable(User user);

	public String getRoleName(User user);
}
