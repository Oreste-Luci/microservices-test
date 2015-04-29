package test.microservices.b.controller;

import test.microservices.b.bean.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Oreste Luci
 */
@RestController
@RequestMapping(value = "/ms-b")
public class MessageController {

    private final AtomicLong counter = new AtomicLong();


    @RequestMapping(
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Message simpleMessage(@RequestParam(value="lines", required=false, defaultValue="Stranger") String lines) {
        System.out.println("MessageController.simpleMessage(" + lines + ")");
        System.out.println("lines: " + lines);

        int count = Integer.parseInt(lines);

        StringBuffer content = new StringBuffer();

        String text = UUID.randomUUID().toString();

        for (int i=0;i<count;i++) {
            content.append(i+1).append(". ").append(text).append("\n");
        }

        return new Message(counter.incrementAndGet(), content.toString());
    }


    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMessage",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Message longMessage(@RequestParam(value="lines", required=false, defaultValue="1000") String lines) {
        System.out.println("MessageController.longMessage(" + lines + ")");
        System.out.println("lines: " + lines);

        int count = Integer.parseInt(lines);

        StringBuffer content = new StringBuffer();

        String text = UUID.randomUUID().toString();

        for (int i=0;i<count;i++) {
            content.append(i+1).append(". ").append(text).append("\n");
        }

        return new Message(counter.incrementAndGet(), content.toString());
    }
}
