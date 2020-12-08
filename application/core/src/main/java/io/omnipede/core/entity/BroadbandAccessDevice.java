package io.omnipede.core.entity;

public class BroadbandAccessDevice {

    private Exchange exchange;

    private String hostname;
    private String serialNumber;
    private DeviceType deviceType;
    private Integer availablePorts = 0;

    public BroadbandAccessDevice(String hostname, String serialNumber, DeviceType deviceType) {
        this.hostname = hostname;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
    }

    public String getHostname() {
        return hostname;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Integer getAvailablePorts() {
        return availablePorts;
    }

    public void setAvailablePorts(Integer availablePorts) {
        this.availablePorts = availablePorts;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }
}
