package test.microservices.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Oreste Luci
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MSTestAConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(MSTestAConfiguration.class, args);
    }
}
