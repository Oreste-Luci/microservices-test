package test.microservices.a.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import test.microservices.a.bean.Meassure;
import test.microservices.a.bean.MeassureGroup;
import test.microservices.a.bean.Message;
import test.microservices.a.bean.MessageMetric;
import test.microservices.a.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;

/**
 * @author Oreste Luci
 */
@Controller
@RequestMapping("/clientmetrics")
public class MessageController {

    @Autowired
    MessageService messageService;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return "clientmetrics";
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/direct",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message direct(@RequestParam(value="server", required=false, defaultValue="localhost") String server,
                                        @RequestParam(value="port", required=false, defaultValue="8080") String port) {
        System.out.println("MessageController.direct(" + port + ")");
        return messageService.direct(server, port, "1");
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/directEureka",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Message eurkaDirect() {
        System.out.println("MessageController.eurkaDirect -> Calling service layer");
        return messageService.eurkaDirect("1");
    }



    @RequestMapping(
            method= RequestMethod.GET,
            value = "/eurekaNextServer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Meassure eurekaNextServer(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                              @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {
        System.out.println("MessageController.eurekaNextServer(" + calls + "," + lines + ")");

        Meassure meassure = new Meassure();
        meassure.setCalls(calls);
        meassure.setLines(lines);

        String libesStr = "" + lines;

        BigInteger sum = BigInteger.ZERO;
        long start,time;

        for (int i=0;i<calls.intValue();i++) {

            start = System.nanoTime();
            Message message = messageService.eurekaNextServer(libesStr);
            time = System.nanoTime() - start;

            message.setTimeTaken(time);

            sum = sum.add(new BigInteger("" + time));
        }

        sum = sum.divide(new BigInteger(""+calls.intValue()));

        meassure.setTimeTaken(sum.longValue());

        return meassure;
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/loadBalancer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Message useLoadBalancer() {
        System.out.println("MessageController.useLoadBalancer -> Calling service layer");
        return messageService.useLoadBalancer("1");
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/feign",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message feign() {
        System.out.println("MessageController.feign");
        return messageService.feign("1");
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMessageFeign",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Meassure longMessageFeign(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                              @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        System.out.println("MessageController.longMessageBalancer(" + calls + "," + lines + ")");

        Meassure meassure = new Meassure();
        meassure.setCalls(calls);
        meassure.setLines(lines);

        String libesStr = "" + lines;

        BigInteger sum = BigInteger.ZERO;
        long start,time;

        for (int i=0;i<calls.intValue();i++) {

            start = System.nanoTime();
            Message message = messageService.longMessageFeign(libesStr);
            time = System.nanoTime() - start;

            message.setTimeTaken(time);

            sum = sum.add(new BigInteger("" + time));
        }

        sum = sum.divide(new BigInteger(""+calls.intValue()));

        meassure.setTimeTaken(sum.longValue());

        return meassure;
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMessageBalancer",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Meassure longMessageBalancer(@RequestParam(value="calls", required=false, defaultValue="100") Integer calls,
                                       @RequestParam(value="lines", required=false, defaultValue="1000") Integer lines) {

        System.out.println("MessageController.longMessageBalancer(" + calls + "," + lines + ")");

        Meassure meassure = new Meassure();
        meassure.setCalls(calls);
        meassure.setLines(lines);

        String libesStr = "" + lines;

        BigInteger sum = BigInteger.ZERO;
        long start,time;

        for (int i=0;i<calls.intValue();i++) {

            start = System.nanoTime();
            Message message = messageService.longMessageBalancer(libesStr);
            time = System.nanoTime() - start;

            message.setTimeTaken(time);

            sum = sum.add(new BigInteger("" + time));
        }

        sum = sum.divide(new BigInteger(""+calls.intValue()));

        meassure.setTimeTaken(sum.longValue());

        return meassure;
    }






    @RequestMapping(
            method= RequestMethod.GET,
            value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message test() {
        System.out.println("MessageController.test");

        Message message = new Message();
        message.setId(100);
        message.setContent("Test Method");

        return message;
    }
}
