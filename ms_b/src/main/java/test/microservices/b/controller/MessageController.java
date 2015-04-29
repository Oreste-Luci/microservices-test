package test.microservices.b.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import test.microservices.b.bean.Message;
import test.microservices.b.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Oreste Luci
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    FileService fileService;

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Message sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        System.out.println("name: " + name);
        return new Message(counter.incrementAndGet(), name + " - B");
    }

    /*
    @RequestMapping(value = "/largeContent", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource largeContent() {

        System.out.println("MessageController.largeContent");

        File file = new File("C:\\Users\\Oluci\\Temp\\large_file.txt");

        return new FileSystemResource(file);
    }
    */
}
