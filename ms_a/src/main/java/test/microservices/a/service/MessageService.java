package test.microservices.a.service;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import test.microservices.a.bean.Message;

import java.net.URI;
import java.util.List;

/**
 * @author Oreste Luci
 */
@Component
public class MessageService {

    private static final String SERVICE_APP = "MICROSERVICES_TEST_APP_B";

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

        List<ServiceInstance> messageServices = springDiscoveryClient.getInstances(this.SERVICE_APP);

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

        InstanceInfo instance = netFlixDiscoveryClient.getNextServerFromEureka(this.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }

    public Message useLoadBalancer(String name) {

        System.out.println("MessageService.useLoadBalancer: Sending to MS B: " + name);

        ServiceInstance instance = loadBalancer.choose(this.SERVICE_APP);

        URI serviceURI = instance.getUri();

        String url = serviceURI.toString() + "message?name=" + name;

        System.out.println("callURI: " + url);

        RestTemplate restTemplate = new RestTemplate();

        Message message = restTemplate.getForObject(url, Message.class);

        return message;
    }
}
