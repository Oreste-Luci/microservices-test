package test.microservices.a.controller;

import test.microservices.a.bean.Message;
import test.microservices.a.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Oreste Luci on 22/04/2015.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

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
