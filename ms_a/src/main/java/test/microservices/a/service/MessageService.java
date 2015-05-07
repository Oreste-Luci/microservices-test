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
import test.microservices.a.bean.MessageMetric;

import java.net.URI;
import java.util.List;

/**
 * @author Oreste Luci
 */
@Service
@EnableFeignClients
public class MessageService {

    private static final String SERVICE_APP = "MS-TEST-B";
    private static final String SERVICE_B_PATH = "ms-b";

    @Autowired
    MicroserviceB microserviceB;

    @Autowired
    private com.netflix.discovery.DiscoveryClient netFlixDiscoveryClient;

    @Autowired
    private org.springframework.cloud.client.discovery.DiscoveryClient springDiscoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;


    public Message direct(String server, String port,String lines) {

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + server + ":" + port + "/" + MessageService.SERVICE_B_PATH + "?lines=" + lines;

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }


    public Message eurkaDirect(String lines) {

        List<ServiceInstance> messageServices = springDiscoveryClient.getInstances(MessageService.SERVICE_APP);

        for (ServiceInstance instance : messageServices) {
            System.out.println(instance.getServiceId());
        }

        ServiceInstance serviceInstance = messageServices.get(0);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + MessageService.SERVICE_B_PATH + "?lines=" + lines;

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }


    public Message eurekaNextServer(String lines) {

        InstanceInfo instance = netFlixDiscoveryClient.getNextServerFromEureka(MessageService.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/" + MessageService.SERVICE_B_PATH + "?lines=" + lines;

        Message message = restTemplate.getForObject(callURI, Message.class);

        return message;
    }


    public Message useLoadBalancer(String lines) {
        ServiceInstance instance = loadBalancer.choose(MessageService.SERVICE_APP);

        URI serviceURI = instance.getUri();

        String url = serviceURI.toString() + MessageService.SERVICE_B_PATH + "?name=" + lines;

        RestTemplate restTemplate = new RestTemplate();

        Message message = restTemplate.getForObject(url, Message.class);

        return message;
    }

    public Message longMessageBalancer(String lines) {

        ServiceInstance instance = loadBalancer.choose(MessageService.SERVICE_APP);

        URI serviceURI = instance.getUri();

        StringBuilder url = new StringBuilder(serviceURI.toString()).append(MessageService.SERVICE_B_PATH).append("/longMessage?lines=").append(lines);

        RestTemplate restTemplate = new RestTemplate();

        Message message = restTemplate.getForObject(url.toString(), Message.class);

        return message;
    }

    public Message feign(String lines) {
       return microserviceB.getMessage(lines);
    }


    public Message longMessageFeign(String lines) {
       return microserviceB.getLongMessage(lines);
    }




    @FeignClient(value = MessageService.SERVICE_APP)
    public interface MicroserviceB {

        @RequestMapping(value = "/" + MessageService.SERVICE_B_PATH + "?name={name}", method = RequestMethod.GET)
        Message getMessage(@PathVariable(value = "name") String name);

        @RequestMapping(value = "/" + MessageService.SERVICE_B_PATH + "/longMessage?lines={lines}", method = RequestMethod.GET)
        Message getLongMessage(@PathVariable(value = "lines") String lines);
    }
}
