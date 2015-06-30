package org.hinsteny.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
	
	 /**
      * 获取 user 信息
	  * @param params
	  * @return
	  */
    public Map<String,Object> get(Map<String, Object> params);

    /**
     * 添加 user
     * @param params
     * @return
     */
    public void create(Map<String, Object> params);
    
}
