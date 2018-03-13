package ch.hevs.sdi.helloamqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageConverter extends Jackson2JsonMessageConverter {
    private @Value("${ch.hevs.sdi.helloamqp.content-type}") String contentType;

    @Override
    public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {
        message.getMessageProperties().setContentType(contentType);
        return super.fromMessage(message, conversionHint);
    }
}
