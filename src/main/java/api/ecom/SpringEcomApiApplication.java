package api.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
        },
        scanBasePackages = {
                "api.ecom",       // main package
                "Config",         // if not already under api.ecom
                "controllers",
                "Service",
                "repository",
                "Entity",
                "util"
        }
)
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "Entity")
public class SpringEcomApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEcomApiApplication.class, args);
    }

}
