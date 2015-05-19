package test.microservices.a.service;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import test.microservices.a.bean.MeassureGroup;
import test.microservices.a.bean.MeassureGroups;
import test.microservices.a.bean.MessageMetric;
import test.microservices.a.bean.protos.MessageMetricProtos;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

/**
 * Created by olivernoguera on 07/05/2015.
 */
@Service
@EnableFeignClients
public class NetworkProtosService {

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
            MessageMetricProtos.MessageMetric messageMetric = sender.process(this);
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



    public MessageMetricProtos.MessageMetric direct(String server, String port,Integer lines) {

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + server + ":" + port + "/" + NetworkProtosService.SERVICE_B_PATH  + "/longMetricProtoMessage?lines={" + lines+"}";

        MessageMetricProtos.MessageMetric message = restTemplate.getForObject(callURI, MessageMetricProtos.MessageMetric.class);

        return message;
    }


    public MessageMetricProtos.MessageMetric eurkaDirect(Integer lines) {

        List<ServiceInstance> messageServices = springDiscoveryClient.getInstances(NetworkProtosService.SERVICE_APP);

        for (ServiceInstance instance : messageServices) {
            System.out.println(instance.getServiceId());
        }

        ServiceInstance serviceInstance = messageServices.get(0);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + NetworkProtosService.SERVICE_B_PATH + "/longMetricProtoMessage?lines={" + lines+"}";

        MessageMetricProtos.MessageMetric message = restTemplate.getForObject(callURI, MessageMetricProtos.MessageMetric.class);

        return message;
    }


    public MessageMetricProtos.MessageMetric eurekaNextServer(Integer lines) {

        InstanceInfo instance = netFlixDiscoveryClient.getNextServerFromEureka(NetworkProtosService.SERVICE_APP, false);

        RestTemplate restTemplate = new RestTemplate();

        String callURI = instance.getHomePageUrl() + "/" + NetworkProtosService.SERVICE_B_PATH + "/longMetricProtoMessage?lines={" + lines+"}";

        MessageMetricProtos.MessageMetric message = restTemplate.getForObject(callURI, MessageMetricProtos.MessageMetric.class);

        return message;
    }


    public MessageMetricProtos.MessageMetric useLoadBalancer(Integer lines) {
        ServiceInstance instance = loadBalancer.choose(NetworkProtosService.SERVICE_APP);

        URI serviceURI = instance.getUri();

        String url = serviceURI.toString() + NetworkProtosService.SERVICE_B_PATH + "/longMetricProtoMessage?lines={" + lines+"}";

        RestTemplate restTemplate = new RestTemplate();

        MessageMetricProtos.MessageMetric message = restTemplate.getForObject(url, MessageMetricProtos.MessageMetric.class);

        return message;
    }

    public MessageMetricProtos.MessageMetric longMessageTransferFeign(Integer lines) {
       return microserviceB.getMessageMetric( "" + lines);
    }



    public MessageMetricProtos.MessageMetric longMessageTransferFeign(Integer calls, Integer lines) {

        MeassureGroups meassureGroups = new MeassureGroups(calls,lines);
        MeassureGroup networkMeassure = new MeassureGroup();
        MeassureGroup generationMeassure = new MeassureGroup();


        BigInteger sumNetwork = BigInteger.ZERO;
        BigInteger sumTest = BigInteger.ZERO;
        long start,time;

        for (int i = 0 ; i< calls.intValue() ; i++) {

            start = System.nanoTime();
            //MessageMetric messageMetric = messageService.genericSender(libesStr);
            MessageMetricProtos.MessageMetric messageMetric =  microserviceB.getMessageMetric( "" + lines);
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
        return MessageMetricProtos.MessageMetric.newBuilder().setGeneratingTime(sumNetwork.longValue()).setMessages(
                MessageMetricProtos.MessageMetric.Message.newBuilder().setContent("Max").setId(1L).build()
        ).build();

    }


    public  interface Sender{
        public MessageMetricProtos.MessageMetric process(NetworkProtosService networkService);
    }

    @FeignClient(value = NetworkProtosService.SERVICE_APP)
    public interface MicroserviceB {

        @RequestMapping(value = "/" + NetworkProtosService.SERVICE_B_PATH + "/longMetricProtoMessage?lines={lines}", method = RequestMethod.GET,
                consumes = {  "application/x-protobuf", MediaType.APPLICATION_JSON_VALUE})
        MessageMetricProtos.MessageMetric getMessageMetric(@PathVariable(value = "lines") String lines);
    }

}
