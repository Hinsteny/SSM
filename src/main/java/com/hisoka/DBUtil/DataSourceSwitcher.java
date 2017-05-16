package com.hisoka.DBUtil;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/12
 * @copyright: 2016 All rights reserved.
 */
public class DataSourceSwitcher {

    private static final ThreadLocal<String> dbContextHolder = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> dbContextReadWriteSeparate = new ThreadLocal<>();

    public static void setDataSourceKeyInContext(String dataSourceKey) {
        dbContextHolder.set(dataSourceKey);
        dbContextReadWriteSeparate.set(true);
    }

    /**
     * 强制设置当前数据源到主库数据源上下文中，即本次线程生命周期内读请求都走主库(
     * 即使读写分离开关readWriteSeparateFlag为true读请求也会强制走主库)
     *
     */
    public static void setDataSourceKeyForceMaster() {
        setDataSourceKeyForceMaster(null);
    }

    /**
     * 强制设置数据源到主库数据源上下文中，即本次线程生命周期内读请求都走主库(
     * 即使读写分离开关readWriteSeparateFlag为true读请求也会强制走主库)
     *
     * @param dataSourceKey
     */
    public static void setDataSourceKeyForceMaster(String dataSourceKey) {
        if (dataSourceKey != null) {
            dbContextHolder.set(dataSourceKey);
        }

        dbContextReadWriteSeparate.set(false);
    }

    /**
     * 强制设置当前数据源到备库数据源上下文中，即本次线程生命周期内读请求都走备库(
     * 即使读写分离开关readWriteSeparateFlag为false读请求也会强制走备库)
     *
     */
    public static void setDataSourceKeyForceSlave() {
        setDataSourceKeyForceSlave(null);
    }

    /**
     * 强制设置数据源到备库数据源上下文中，即本次线程生命周期内读请求都走备库(
     * 即使读写分离开关readWriteSeparateFlag为false读请求也会强制走备库)
     *
     * @param dataSourceKey
     */
    public static void setDataSourceKeyForceSlave(String dataSourceKey) {
        if (dataSourceKey != null) {
            dbContextHolder.set(dataSourceKey);
        }

        dbContextReadWriteSeparate.set(true);
    }

    public static String getDataSourceKey() {
        // 目前支持从线程上下文中获取，以后提供扩展接口，允许用户实现具体的获取方式
        return getDataSourceTypeFromContext();
    }

    public static String getDataSourceTypeFromContext() {
        String dataSourceKey = (String) dbContextHolder.get();
        return dataSourceKey;
    }

    public static Boolean getReadWriteSeparateFromContext() {
        Boolean readWriteSeparateFlag = dbContextReadWriteSeparate.get();
        return readWriteSeparateFlag;
    }

    public static void clearDataSourceKey() {
        dbContextHolder.remove();
        dbContextReadWriteSeparate.remove();
    }
}
