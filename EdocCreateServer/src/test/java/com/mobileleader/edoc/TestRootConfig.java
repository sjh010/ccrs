package com.mobileleader.edoc;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.mobileleader.edoc.db.mapper"})
public class TestRootConfig {
	
	@Bean(name = "dataSource", destroyMethod="")
	public DataSource dataSource() {
		DataSource 
			dataSource = getJdbcDataSource();

		return dataSource;
	}
	
	private DataSource getJdbcDataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
		dataSource.setUrl("jdbc:oracle:thin:@192.168.12.42:1521:ccrsbetest");
		dataSource.setUsername("PPL_TRAN");
		dataSource.setPassword("ppltran!234p");
		
		return dataSource;
	}
	
	@Bean//(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTransactionFactory(new SpringManagedTransactionFactory());
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(
				resolver.getResources("classpath*:com/mobileleader/edoc/db/mapper/*.xml"));
		
		Configuration configuration = new Configuration();
		
		configuration.setJdbcTypeForNull(null);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCallSettersOnNulls(false);
		sqlSessionFactory.setConfiguration(configuration);
		
		sqlSessionFactory.setTypeAliasesPackage("com.mobileleader.edoc.db.dto");
		
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
}
