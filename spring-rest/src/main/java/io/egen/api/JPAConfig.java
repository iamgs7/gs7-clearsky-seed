package io.egen.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class JPAConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean lcemf() {
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
		lcemf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lcemf.setDataSource(getDataSource());
		lcemf.setPackagesToScan("io.egen.api.entity");
		lcemf.setJpaProperties(jpaProperties());
		return lcemf;
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dSource = new DriverManagerDataSource();
		dSource.setDriverClassName("com.mysql.jdbc.Driver");
		dSource.setUrl(env.getProperty("db.url"));
		dSource.setUsername(env.getProperty("db.user", "root"));
		dSource.setPassword(env.getProperty("db.password", "abcdef"));

		return dSource;
	}

	@Bean
	public PlatformTransactionManager txnMgr(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show.sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format.sql"));
		return properties;

	}
}
