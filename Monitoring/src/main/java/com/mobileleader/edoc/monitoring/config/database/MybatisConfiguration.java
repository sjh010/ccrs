package com.mobileleader.edoc.monitoring.config.database;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@MapperScan("com.mobileleader.edoc.monitoring.db.mapper")
@RequiredArgsConstructor
public class MybatisConfiguration {

    private final DataSource dataSource;
    
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.mobileleader.edoc.monitoring.db.dto");
        return sessionFactory;
    }
}
