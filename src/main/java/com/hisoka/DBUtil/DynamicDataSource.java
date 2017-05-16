package com.hisoka.DBUtil;

import com.hisoka.exception.ApplicationRunTimeException;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/16
 * @copyright: 2016 All rights reserved.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private String currentDataSource;

    private String defaultTargetDataSourceKey;

    public String getCurrentDataSource() {
        return currentDataSource;
    }

    public void setDefaultTargetDataSourceKey(String defaultTargetDataSourceKey) {
        this.defaultTargetDataSourceKey = defaultTargetDataSourceKey;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceSwitcher.getDataSourceKey();

        if (dataSource == null) {
            dataSource = defaultTargetDataSourceKey;
        }

        if (dataSource == null) {
            throw new ApplicationRunTimeException("Can not find a dataSource.");
        }

        this.currentDataSource = dataSource;
        return dataSource;
    }
}
