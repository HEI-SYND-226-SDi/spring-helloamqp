package ch.hevs.sdi.helloamqp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.dto.Point;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SensorReadingListener {
    private final Log log = LogFactory.getLog(SensorReadingListener.class);

    private @Value("${ch.hevs.sdi.helloamqp.influx-database}") String database;
    private final InfluxDB influx;

    public SensorReadingListener(InfluxDB influx) {
        this.influx = influx;
    }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(),
        exchange = @Exchange(value = "amq.topic", type = ExchangeTypes.TOPIC),
        key = "sdi42.*.sensorreading"
    ))
    private void onSensorReading(@Payload SensorReading reading, Message message) {
        String sensorId = message.getMessageProperties().getReceivedRoutingKey().split("\\.")[1];
        log.info("New Sensor reading: " + sensorId + "-> " + reading.toString());

        try {
            influx.setDatabase(database);
            influx.write(
                Point.measurement("sensorreading")
                    .tag("sensorId", sensorId)
                    .addField("location", reading.getLocation())
                    .addField("temperature", reading.getTemperature())
                    .addField("humidity", reading.getHumidity()).build()
            );
        } catch (InfluxDBException e) {
            log.error(e);
        }
    }
}
