package com.mobileleader.edoc.monitoring.config.database;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.mobileleader.edoc.monitoring.common.type.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/db.properties")
@RequiredArgsConstructor
public class DatabaseConfiguration {

    private final Environment env;
    
    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        DataSource dataSource = null;
        String dataSourceType = env.getProperty("datasource.type");
        if (DataSourceType.JNDI.name().equalsIgnoreCase(dataSourceType)) {
            dataSource = getJndiDataSource();
        } else if (DataSourceType.JDBC.name().equalsIgnoreCase(dataSourceType)) {
            dataSource = getHikariDataSource();
        } else {
            throw new RuntimeException("[Invalid DataSourceType] : " + dataSourceType);
        }
        return dataSource;
    }
    
    private DataSource getHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setJdbcUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setConnectionTestQuery("SELECT 1 FROM DUAL");
        return dataSource;
    }
    
    private DataSource getJndiDataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(false);
        return lookup.getDataSource(env.getProperty("db.jndiname"));
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
