package ch.hevs.sdi.helloamqp;

public class SensorReading {
    private String location;
    private double temperature;
    private double humidity;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return (new StringBuilder())
            .append(", Location=").append(location)
            .append(", Temperature=").append(temperature)
            .append(", Humidity=").append(humidity)
            .toString();
    }
}
