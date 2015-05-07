package test.microservices.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.microservices.a.bean.MeassureGroup;
import test.microservices.a.bean.MeassureGroups;
import test.microservices.a.bean.MessageMetric;
import test.microservices.a.service.MessageService;

import java.math.BigInteger;

/**
 * Created by olivernoguera on 07/05/2015.
 */

@Controller
@RequestMapping("/networkmetrics")
public class NetworkMetricsController {

    @Autowired
    MessageService messageService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String networkmetrics() {
        return "networkmetrics";
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMessageFeign",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroups longMessageTransferFeign(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                           @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {
        MeassureGroups meassureGroups = new MeassureGroups(calls,lines);

        MeassureGroup networkMeassure = new MeassureGroup();
        MeassureGroup generationMeassure = new MeassureGroup();

        String libesStr = "" + lines;
        BigInteger sumNetwork = BigInteger.ZERO;
        BigInteger sumTest = BigInteger.ZERO;
        long start,time;

        for (int i = 0 ; i< calls.intValue() ; i++) {

            start = System.nanoTime();
            MessageMetric messageMetric = messageService.longMessageTransferFeign(libesStr);
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
        meassureGroups.put("generationMessage",generationMeassure);
        return meassureGroups;
    }
}
