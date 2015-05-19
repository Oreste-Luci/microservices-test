package test.microservices.b.controller;

import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import test.microservices.b.bean.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import test.microservices.b.bean.MessageMetric;
import test.microservices.b.bean.protos.MessageMetricProtos;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Oreste Luci
 */
@RestController
@RequestMapping(value = "/ms-b")
public class MessageController {

    private final AtomicLong counter = new AtomicLong();

    private long count;

    @RequestMapping(
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public Message simpleMessage(@RequestParam(value="lines", required=false, defaultValue="1") String lines) {
        System.out.println((++count) + ". MessageController.simpleMessage(" + lines + ")");
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
        System.out.println((++count) + ". MessageController.longMessage(" + lines + ")");
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
            value = "/longMetricMessage",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageMetric longMetricMessage(@RequestParam(value="lines", required=false, defaultValue="1000") String lines) {
        long start,time;
        start = System.nanoTime();

        //System.out.println((++count) + ". MessageController.longMetricMessage(" + lines + ")");
        //System.out.println("lines: " + lines);
        MessageMetric messageMetric = new MessageMetric();
        int count = Integer.parseInt(lines);

        StringBuffer content = new StringBuffer();

        String text = UUID.randomUUID().toString();

        for (int i=0;i<count;i++) {
            content.append(i+1).append(". ").append(text).append("\n");
        }
        Message message = new Message(counter.incrementAndGet(), content.toString());
        messageMetric.setMessage(message);
        messageMetric.setGeneratingTime(System.nanoTime() - start);
        return messageMetric;
    }

    @RequestMapping(
            method= RequestMethod.GET,
            value = "/longMetricProtoMessage")
            //produces = "application/x-protobuf "+MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MessageMetricProtos.MessageMetric longMetricProtoMessage(@RequestParam(value="lines", required=false, defaultValue="1000") String lines) {
        long start,time;
        start = System.nanoTime();

        //System.out.println((++count) + ". MessageController.longMetricProtoMessage(" + lines + ")");
        //System.out.println("lines: " + lines);

        int count = Integer.parseInt(lines);

        StringBuffer content = new StringBuffer();

        String text = UUID.randomUUID().toString();

        for (int i=0;i<count;i++) {
            content.append(i+1).append(". ").append(text).append("\n");
        }
        Message message = new Message(counter.incrementAndGet(), content.toString());
        return MessageMetricProtos.MessageMetric.newBuilder().setGeneratingTime(System.nanoTime() - start).setMessages(
                MessageMetricProtos.MessageMetric.Message.newBuilder().setContent(message.getContent()).setId(message.getId()).build()
        ).build();


    }

}
