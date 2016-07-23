package org.hinsteny.service;

import org.hinsteny.action.IndexAction;
import org.hinsteny.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public interface BaseService<T> {

	public T find(T t);

	public boolean create(T t);

	public List<T> listUsers(T t);
}
