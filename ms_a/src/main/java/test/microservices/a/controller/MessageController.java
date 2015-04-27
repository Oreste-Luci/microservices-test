package test.microservices.a.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import test.microservices.a.bean.Message;
import test.microservices.a.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Oreste Luci
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Message direct(@RequestParam(value="port", required=false, defaultValue="8080") String port) {
        System.out.println("port: " + port);
        return messageService.direct(port,"A");
    }

    @RequestMapping(method= RequestMethod.GET, value = "/directEureka")
    public @ResponseBody
    Message eurkaDirect() {
        System.out.println("MessageController.eurkaDirect -> Calling service layer");
        return messageService.eurkaDirect("A");
    }

    @RequestMapping(method= RequestMethod.GET, value = "/eurekaNextServer")
    public @ResponseBody
    Message eurekaNextServer() {
        System.out.println("MessageController.eurekaNextServer -> Calling service layer");
        return messageService.eurekaNextServer("A");
    }

    @RequestMapping(method= RequestMethod.GET, value = "/loadBalancer")
    public @ResponseBody
    Message useLoadBalancer() {
        System.out.println("MessageController.useLoadBalancer -> Calling service layer");
        return messageService.useLoadBalancer("A");
    }
}
