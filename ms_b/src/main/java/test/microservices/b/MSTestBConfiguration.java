package test.microservices.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Oreste Luci
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MSTestBConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(MSTestBConfiguration.class, args);
    }
}
