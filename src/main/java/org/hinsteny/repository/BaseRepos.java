package org.hinsteny.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface BaseRepos<T> {

    /**
     * 获取单个实例对象
     *
     * @param t
     * @return
     */
    public T get(@Param(value = "t")T t);

    /**
     * 保存实例
     *
     * @param t
     * @return
     */
    public void save(@Param(value = "t")T t);

    /**
     * 查询实例列表
     *
     * @param query
     * @return
     */
    public List<T> query(@Param(value = "t")T t, @Param(value = "param")Map<String, Object> query);

    /**
     * 更新实例
     *
     * @param t
     * @return
     */
    public void update(@Param(value = "t")T t);

    /**
     * 保存实例
     *
     * @param t
     * @return
     */
    public void delete(@Param(value = "t")T t, @Param(value = "param")Map<String, Object> param);
}
