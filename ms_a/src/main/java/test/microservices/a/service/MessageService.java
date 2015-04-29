package test.microservices.a.service;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import test.microservices.a.bean.Message;

import java.net.URI;
import java.util.List;

/**
 * @author Oreste Luci
 */
@Component
@Service
@EnableFeignClients
public class MessageService {

    private static final String SERVICE_APP = "MS-TEST-B";

    @Autowired
    MicroserviceB microserviceB;

    @Autowired
    private com.netflix.discovery.DiscoveryClient netFlixDiscoveryClient;

    @Autowired
    private org.springframework.cloud.client.discovery.DiscoveryClient springDiscoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    public Message direct(String port,String name) {

        System.out.println("MessageService.direct: Sending to MS B: " + name);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://localhost:" + port + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }

    public Message eurkaDirect(String name) {

        System.out.println("MessageService.eurkaDirect: Sending to MS B: " + name);

        List<ServiceInstance> messageServices = springDiscoveryClient.getInstances(MessageService.SERVICE_APP);

        System.out.println("Instances obtained: " + messageServices.size());

        for (ServiceInstance instance : messageServices) {
            System.out.println(instance.getServiceId());
        }

        ServiceInstance serviceInstance = messageServices.get(0);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }

    public Message eurekaNextServer(String name) {

        System.out.println("MessageService.eurekaNextServer: Sending to MS B: " + name);

        InstanceInfo instance = netFlixDiscoveryClient.getNextServerFromEureka(MessageService.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }

    public Message useLoadBalancer(String name) {

        System.out.println("MessageService.useLoadBalancer: Sending to MS B: " + name);

        ServiceInstance instance = loadBalancer.choose(MessageService.SERVICE_APP);

        URI serviceURI = instance.getUri();

        String url = serviceURI.toString() + "message?name=" + name;

        System.out.println("callURI: " + url);

        RestTemplate restTemplate = new RestTemplate();

        Message message = restTemplate.getForObject(url, Message.class);

        return message;
    }

    public Message feign(String name) {
        System.out.println("MessageService.feign: Sending to MS B: " + name);
        return microserviceB.getMessage(name);
    }

    @FeignClient(value = MessageService.SERVICE_APP)
    public interface MicroserviceB {

        @RequestMapping(value = "/message?name={name}", method = RequestMethod.GET)
        Message getMessage(@PathVariable(value = "name") String name);

        @RequestMapping(value = "/longMessage?lines={lines}", method = RequestMethod.GET)
        Message getLongMessage(@PathVariable(value = "lines") Integer lines);
    }
}
