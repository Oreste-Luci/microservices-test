package test.microservices.a.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Oreste Luci
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients("test.microservices.*")
@ComponentScan("test.microservices.*")
public class MSTestAConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(MSTestAConfiguration.class, args);
    }
}
