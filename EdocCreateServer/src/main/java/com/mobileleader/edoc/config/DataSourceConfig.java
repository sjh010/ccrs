package com.mobileleader.edoc.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(basePackages = {"com.mobileleader.edoc.db.mapper"})
public class DataSourceConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Value("#{dbProperty['db.dataSourceType']}")
	private String dataSourceType;
	
	@Value("#{dbProperty['db.jndiName']}")
	private String jndiName;
	
	@Value("#{dbProperty['jdbc.driverClass']}")
	private String jdbcDriverClass;
	
	@Value("#{dbProperty['jdbc.url']}")
	private String jdbcUrl;
	
	@Value("#{dbProperty['jdbc.username']}")
	private String jdbcUsername;
	
	@Value("#{dbProperty['jdbc.password']}")
	private String jdbcPassword;
	
	@Bean(name = "dataSource", destroyMethod="")
	public DataSource dataSource() {
		DataSource dataSource = null;
		
		if (DataSourceType.JNDI.name().equalsIgnoreCase(dataSourceType)) {
			dataSource = getJndiDataSource(jndiName);
		} else if (DataSourceType.JDBC.name().equalsIgnoreCase(dataSourceType)) {
			dataSource = getJdbcDataSource();
		} else {
			logger.error("[DataSource Error]");
		}
		
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		
		sqlSessionFactory.setDataSource(dataSource);
		
		sqlSessionFactory.setTransactionFactory(new SpringManagedTransactionFactory());
		sqlSessionFactory.setTypeAliasesPackage("com.mobileleader.edoc.db.dto");
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(
				resolver.getResources("classpath*:com/mobileleader/edoc/db/mapper/*.xml"));
		
		Configuration configuration = new Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		
		sqlSessionFactory.setConfiguration(configuration);
		
		return sqlSessionFactory;
	}
	
	@Bean(name = "sqlSession", destroyMethod = "clearCache")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);

		return transactionManager;
	}
	
	private DataSource getJdbcDataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		
		return dataSource;
	}

	private DataSource getJndiDataSource(String jndiName) {
		JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
		jndiDataSourceLookup.setResourceRef(true);
		return jndiDataSourceLookup.getDataSource(jndiName);
	}
	
	public enum DataSourceType {
		JNDI, JDBC;
	}
}