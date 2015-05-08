package test.microservices.a.service;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import test.microservices.a.bean.MeassureGroup;
import test.microservices.a.bean.MeassureGroups;
import test.microservices.a.bean.Message;
import test.microservices.a.bean.MessageMetric;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

/**
 * Created by olivernoguera on 07/05/2015.
 */
@Service
@EnableFeignClients
public class NetworkService {

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


    public MeassureGroups genericSender(Integer calls, Integer lines, Sender sender) {
        MeassureGroups meassureGroups = new MeassureGroups(calls,lines);
        MeassureGroup networkMeassure = new MeassureGroup();
        MeassureGroup generationMeassure = new MeassureGroup();


        BigInteger sumNetwork = BigInteger.ZERO;
        BigInteger sumTest = BigInteger.ZERO;
        long start,time;

        for (int i = 0 ; i< calls.intValue() ; i++) {

            start = System.nanoTime();
            //MessageMetric messageMetric = messageService.genericSender(libesStr);
            MessageMetric messageMetric = sender.process(this);
            time = System.nanoTime() - start - messageMetric.getGeneratingTime() ;

            networkMeassure.addTimeMinMax(time);
            generationMeassure.addTimeMinMax( messageMetric.getGeneratingTime());
            sumNetwork = sumNetwork.add(new BigInteger("" + time));
            sumTest = sumTest.add(new BigInteger("" +  messageMetric.getGeneratingTime()));

        }

        sumNetwork = sumNetwork.divide(new BigInteger(""+calls.intValue()));
        sumTest = sumTest.divide(new BigInteger(""+calls.intValue()));
        networkMeassure.setAvgTimeTaken(sumNetwork.longValue());
        generationMeassure.setAvgTimeTaken(sumTest.longValue());

        meassureGroups.put("network",networkMeassure);
        meassureGroups.put("creatingMessages",generationMeassure);
        return meassureGroups;
    }



    public MessageMetric direct(String server, String port,Integer lines) {

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + server + ":" + port + "/" + NetworkService.SERVICE_B_PATH  + "/longMetricMessage?lines={" + lines+"}";

        MessageMetric message = restTemplate.getForObject(callURI, MessageMetric.class);

        return message;
    }


    public MessageMetric eurkaDirect(Integer lines) {

        List<ServiceInstance> messageServices = springDiscoveryClient.getInstances(NetworkService.SERVICE_APP);

        for (ServiceInstance instance : messageServices) {
            System.out.println(instance.getServiceId());
        }

        ServiceInstance serviceInstance = messageServices.get(0);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + NetworkService.SERVICE_B_PATH + "/longMetricMessage?lines={" + lines+"}";

        MessageMetric message = restTemplate.getForObject(callURI, MessageMetric.class);

        return message;
    }


    public MessageMetric eurekaNextServer(Integer lines) {

        InstanceInfo instance = netFlixDiscoveryClient.getNextServerFromEureka(NetworkService.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/" + NetworkService.SERVICE_B_PATH + "/longMetricMessage?lines={" + lines+"}";

        MessageMetric message = restTemplate.getForObject(callURI, MessageMetric.class);

        return message;
    }


    public MessageMetric useLoadBalancer(Integer lines) {
        ServiceInstance instance = loadBalancer.choose(NetworkService.SERVICE_APP);

        URI serviceURI = instance.getUri();

        String url = serviceURI.toString() + NetworkService.SERVICE_B_PATH + "/longMetricMessage?lines={" + lines+"}";

        RestTemplate restTemplate = new RestTemplate();

        MessageMetric message = restTemplate.getForObject(url, MessageMetric.class);

        return message;
    }

    public MessageMetric longMessageTransferFeign(Integer lines) {
         return microserviceB.getMessageMetric( "" + lines);
    }


    public  interface Sender{
        public MessageMetric process(NetworkService networkService);
    }

    @FeignClient(value = NetworkService.SERVICE_APP)
    public interface MicroserviceB {

        @RequestMapping(value = "/" + NetworkService.SERVICE_B_PATH + "/longMetricMessage?lines={lines}", method = RequestMethod.GET)
        MessageMetric getMessageMetric(@PathVariable(value = "lines") String lines);
    }

}
