package test.microservices.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.microservices.a.bean.MeassureGroups;
import test.microservices.a.bean.MessageMetric;
import test.microservices.a.bean.protos.MessageMetricProtos;
import test.microservices.a.service.MessageService;
import test.microservices.a.service.NetworkProtosService;
import test.microservices.a.service.NetworkService;

/**
 * Created by olivernoguera on 07/05/2015.
 */

@Controller
@RequestMapping("/networkprotosmetrics")
public class NetworkProtosMetricsController {


    @Autowired
    NetworkProtosService networkProtosService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String networkprotosmetrics() {
        return "networkprotosmetrics";
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/feignproto",
            produces = "application/x-protobuf"
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MessageMetricProtos.MessageMetric feignProtos(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                           @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {
        return networkProtosService.longMessageTransferFeign(calls, lines);
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/feign",
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups feign(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                            @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {
        return networkProtosService.genericSender(calls, lines,
                netweorkProtosService -> {
                    return networkProtosService.longMessageTransferFeign(lines);
                });
    }




    @RequestMapping(
            method= RequestMethod.GET,
            value = "/direct",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups direct(
            @RequestParam(value="server", required=false, defaultValue="localhost") String server,
            @RequestParam(value="port", required=false, defaultValue="8080") String port,
            @RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                            @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        return networkProtosService.genericSender(calls, lines,
                netweorkProtosService -> {
                    return networkProtosService.direct(server, port, lines);
                });
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/eurekaNextServer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups eurekaNextServer(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                            @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        return networkProtosService.genericSender(calls, lines,
                netweorkService -> {
                    return networkProtosService.eurekaNextServer(lines);
                });
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/useLoadBalancer",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups useLoadBalancer(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                            @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        return networkProtosService.genericSender(calls, lines,
                networkProtosService -> {
                    return networkProtosService.useLoadBalancer(lines);
                });
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/eurkaDirect",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups eurkaDirect(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                            @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        return networkProtosService.genericSender(calls, lines,
                netweorkService -> {
                    return networkProtosService.eurkaDirect(lines);
                });
    }




}
