package com.foresight.usermanagementservicebackend.rappitmq;



import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class Receiver {

/*
   private final OrderEventHandler orderEventHandler;


    @RabbitListener(queues = Config.queueName)
    public void receiveMessage( OrderEventModel orderEvent) {

        System.out.println("Received <" + orderEvent + ">");
        orderEventHandler.handle(orderEvent);
    }


 */

}