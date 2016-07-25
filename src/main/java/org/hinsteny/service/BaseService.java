package org.hinsteny.service;

import java.util.List;

public interface BaseService<T> {

	public T find(T t);

	public boolean create(T t);

	public List<T> listUsers(T t);

	public void delete(T t);

}
