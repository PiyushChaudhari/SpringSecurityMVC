package com.application.config;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.application.*")
@Import(value = { SecurityConfig.class,TilesConfig.class })
@EnableTransactionManagement
@PropertySource("classpath:${spring.profiles.active}-application.properties")
public class AppConfig {

	private static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_DB_STATE = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_CURRENT_SESSION_CONTEXT = "hibernate.current_session_context_class";
	private static final String PROPERTY_NAME_ENABLE_LAZY_LOAD_NO_TRANS = "hibernate.enable_lazy_load_no_trans";
	// private static final String PROPERTY_NAME_HIBERNATE_VALIDATION_MODE =
	// "javax.persistence.validation.mode";

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		LOGGER.info("***********************************");
		LOGGER.info("Application Environment " + env.getRequiredProperty("application.environment"));
		LOGGER.info("***********************************");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_DB_STATE, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DB_STATE));
		properties.put(PROPERTY_NAME_ENABLE_LAZY_LOAD_NO_TRANS,
				env.getRequiredProperty(PROPERTY_NAME_ENABLE_LAZY_LOAD_NO_TRANS));

		// properties.put(PROPERTY_NAME_HIBERNATE_CURRENT_SESSION_CONTEXT,
		// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_CURRENT_SESSION_CONTEXT));

		// properties.put(PROPERTY_NAME_HIBERNATE_VALIDATION_MODE,
		// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_VALIDATION_MODE));
		// properties.put("javax.persistence.validation.mode","none");
		// properties.put("hibernate.validator.autoregister_listeners",false);
		// properties.put("hibernate.validator.apply_to_ddl",false);

		return properties;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:i18n/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

}
