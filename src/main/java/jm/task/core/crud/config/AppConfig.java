package jm.task.core.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
//@EnableTransactionManagement
@ComponentScan(basePackages = "jm.task.core.crud")
public class AppConfig {

    @Bean
    public static EntityManager getEntityManager(EntityManagerFactory emf) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/pp211");
        parameters.put("hibernate.connection.username", "hkl");
        parameters.put("hibernate.connection.password", "secret");
        return emf.createEntityManager(parameters);
    }
    @Bean
    public static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("User");
    }

}
