package test.microservices.a.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import test.microservices.a.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;

/**
 * By Oreste Luci
 */
@Service
public class MessageService {

    private static final String SERVICE_APP = "MICROSERVICES_TEST_APP_B";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    public Message eurkaDirect(String name) {

        System.out.println("MessageService.eurkaDirect: Sending to MS B: " + name);

        /*
        List<ServiceInstance> messageServices = discoveryClient.getInstances(this.SERVICE_APP);
        messageServices.forEach(System.out::println);

        ServiceInstance serviceInstance = messageServices.get(0);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
        */

        return null;
    }


    public Message eurekaNextServer(String name) {

        System.out.println("MessageService.eurekaNextServer: Sending to MS B: " + name);

        InstanceInfo instance = discoveryClient.getNextServerFromEureka(this.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/message?name=" + name;

        System.out.println("callURI: " + callURI);

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }

    public Message useLoadBalancer(String name) {

        System.out.println("MessageService.useLoadBalancer: Sending to MS B: " + name);

        ServiceInstance instance = loadBalancer.choose(this.SERVICE_APP);

        URI serviceURI = URI.create(String.format("http://%s:%s/%s%s", instance.getHost(), instance.getPort(),"message?name=",name));

        System.out.println("callURI: " + serviceURI);

        RestTemplate restTemplate = new RestTemplate();

        Message message = restTemplate.getForObject(serviceURI, Message.class);

        return message;
    }
}
