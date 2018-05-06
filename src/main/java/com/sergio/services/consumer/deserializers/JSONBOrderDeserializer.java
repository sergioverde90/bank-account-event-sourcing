package com.sergio.services.consumer.deserializers;

import com.sergio.model.Order;
import com.sergio.model.WithdrawOrderPlaced;
import com.sergio.services.producers.config.JsonConfigFactory;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

public class JSONBOrderDeserializer implements JsonbDeserializer<Order> {

    @Override
    public Order deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        Order event = null;

        while(parser.hasNext()) {
            JsonParser.Event ev = parser.next();
            if(ev.equals(JsonParser.Event.KEY_NAME)) {
                String key = parser.getString();
                parser.next(); // move to value for 'key'
                try {
                    event = ctx.deserialize(Class.forName(key).asSubclass(Order.class), parser);
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        return event;
    }

    public static void main(String[] args) {
        JsonbBuilder builder = JsonbBuilder.newBuilder();
        builder.withConfig(JsonConfigFactory.config());
        Jsonb jsonb = builder.build();
        WithdrawOrderPlaced as = jsonb.fromJson("{\"com.sergio.model.WithdrawOrderPlaced\":{\"id\":\"697ddc" +
                "1a-29b8-4852-ab41-4cbd1d05133c\",\"instant\":\"2018-05-06T12:10:54.092Z\",\"account\":\"1\"," +
                "\"quantity\":2000.0}}", WithdrawOrderPlaced.class.getGenericSuperclass());
    }

}
