package com.gs.base.service;

import com.gs.base.vo.User;
import com.gs.doc.vo.Document;

public interface UserService {

	public Boolean uploadDocument(Document document);
	public Document getDocument(Document document);
	public Boolean deleteDocument(Document document);
	public Boolean updateDocument(Document document);
	
	public User retrieveUser(User user);
	public User createUser(User user);
	public Boolean updateUser(User user);
	public Boolean deleteUser(User user);

}
