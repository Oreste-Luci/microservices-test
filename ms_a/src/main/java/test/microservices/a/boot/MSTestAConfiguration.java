package test.microservices.a.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Oreste Luci
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("test.microservices.*")
public class MSTestAConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(MSTestAConfiguration.class, args);
    }
}
