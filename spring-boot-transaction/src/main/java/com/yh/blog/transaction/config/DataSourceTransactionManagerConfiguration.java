package com.yh.blog.transaction.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName TransactionManagerConfig.java
 * @Description 编程式事务注入
 * @Param
 * @createTime 2020年01月03日 09:57:00
 * //
 */
@Configuration
public class DataSourceTransactionManagerConfiguration {

    @Autowired
    private DataSource dataSource;

//    @Bean(name = "transactionManager")
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }



}
