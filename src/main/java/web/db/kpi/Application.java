package web.db.kpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import redis.clients.jedis.Jedis;

import java.util.Properties;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        Jedis jedis = new Jedis("localhost");
        String user = jedis.get("bduser");
        String password = jedis.get("bdpassword");
        String url = jedis.get("bdurl");
        Properties properties = new Properties();

        properties.put("spring.jpa.show-sql",true);
        properties.put("spring.jpa.properties.hibernate.hbm2ddl.auto","update");
        properties.put("logging.level.org.atmosphere","warn");
        properties.put("vaadin.compatibilityMode",false);
        properties.put("server.port",8080);
        properties.put("spring.datasource.url",url);
        properties.put("spring.datasource.username",user);
        properties.put("spring.datasource.password",password);

        application.setDefaultProperties(properties);
        application.run(args);
    }

}
