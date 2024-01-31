package com.foresight.usermanagementservicebackend.rappitmq;

import com.foresight.usermanagementservicebackend.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserEventPublisher
{

    private final RabbitTemplate rabbitTemplate;


    public void publish(UserDto user)
    {
        try
        {
            rabbitTemplate.convertAndSend(Config.topicExchangeName, "user.created.event",convertObjectToMap(user));

        }
        catch (Exception ex)
        {

        }
    }
    public static Map<String, Object> convertObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
}
