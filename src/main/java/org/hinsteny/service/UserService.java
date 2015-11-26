package org.hinsteny.service;

import java.util.HashMap;
import java.util.List;

public interface UserService {
	
	public boolean create(HashMap<String, Object> param);

	public List<HashMap<String, Object>> listUsers(HashMap<String, Object> param);
}
