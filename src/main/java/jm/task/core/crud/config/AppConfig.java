package jm.task.core.crud.config;

import jm.task.core.crud.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "jm.task.core.crud")
public class AppConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment env;

    public AppConfig(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
        this.env = env;
    }

    @Bean
    @Primary
    public EntityManager getEntityManager(EntityManagerFactory emf) {
        /*Map<String, String> parameters = new HashMap<>();

        parameters.put("hibernate.connection.url", env.getProperty("db.url"));
        parameters.put("hibernate.connection.username", env.getProperty("db.user"));
        parameters.put("hibernate.connection.password", env.getProperty("db.pass"));*/
        System.out.println("Thread_em_id: " + Thread.currentThread().getId());
        //return emf.createEntityManager(parameters);
        return emf.createEntityManager();
    }
    /*@Bean
    public static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("User");
    }*/
/*    @Bean
    public EntityManagerFactory getEmf(LocalContainerEntityManagerFactoryBean emfb) {
        System.out.println("Thread_emf_id: " + Thread.currentThread().getId());
        return emfb.getObject();
    }*/
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();

        emfb.setDataSource(dataSource);
        emfb.setPackagesToScan(User.class.getPackageName());
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        //emfb.setPersistenceUnitName("User");

//        Properties props = new Properties();
//        props.put("hibernate.show_sql", envt.getProperty("hibernate.show_sql"));
//        props.put("hibernate.hbm2ddl.auto", envt.getProperty("hibernate.hbm2ddl.auto"));
//        props.put("hibernate.dialect", envt.getProperty("hibernate.dialect"));
//        props.put("characterEncoding", envt.getProperty("characterEncoding"));
//        props.put("useUnicode", envt.getProperty("useUnicode"));
//        emfb.setJpaProperties(props);
        System.out.println("Thread_id: " + Thread.currentThread().getId());
        return emfb;
    }
    @Bean
    public DataSource makeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("jdbc:mysql://localhost:3306/pp211");
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.pass"));
        return dataSource;

    }
    @Bean
    public JpaTransactionManager getTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        registry.viewResolver(resolver);
    }

}
