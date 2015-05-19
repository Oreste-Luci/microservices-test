package test.microservices.b.config;

import net.bull.javamelody.MonitoringFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

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


    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

}
