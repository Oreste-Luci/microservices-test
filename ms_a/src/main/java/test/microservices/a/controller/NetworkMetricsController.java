package test.microservices.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.microservices.a.bean.MeassureGroup;
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
    public String home() {
        return "networkmetrics";
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMessageFeign",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    MeassureGroup longMessageTransferFeign(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                           @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        System.out.println("MessageController.longMessageTransferFeign(" + calls + "," + lines + ")");

        MeassureGroup meassureGroup = new MeassureGroup();
        meassureGroup.setCalls(calls);
        meassureGroup.setLines(lines);
        MeassureGroup test = new MeassureGroup();
        String libesStr = "" + lines;

        BigInteger sum = BigInteger.ZERO;
        BigInteger sumTest = BigInteger.ZERO;
        long start,time;
        for (int i=0;i<calls.intValue();i++) {

            start = System.nanoTime();
            MessageMetric messageMetric = messageService.longMessageTransferFeign(libesStr);
            time = System.nanoTime() - start - messageMetric.getGeneratingTime() ;


            sumTest = sumTest.add(new BigInteger("" +  messageMetric.getGeneratingTime()));
            meassureGroup.setAvgTimeTaken(time);
            sum = sum.add(new BigInteger("" + time));
            meassureGroup.addTimeMinMax(time);

        }

        sum = sum.divide(new BigInteger(""+calls.intValue()));

        meassureGroup.setAvgTimeTaken(sum.longValue());

        return meassureGroup;
    }
}
