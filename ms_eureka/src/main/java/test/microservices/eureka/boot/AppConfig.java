package test.microservices.eureka.boot;

import net.bull.javamelody.MonitoringFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author Oreste Luci
 */
@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        Filter javaMelodyFilter = new MonitoringFilter();
        FilterRegistrationBean javaMelodyFilterBean = new FilterRegistrationBean(javaMelodyFilter);
        javaMelodyFilterBean.addServletNames("monitoring");
        javaMelodyFilterBean.addUrlPatterns("/*");
        return javaMelodyFilterBean;
    }
}
