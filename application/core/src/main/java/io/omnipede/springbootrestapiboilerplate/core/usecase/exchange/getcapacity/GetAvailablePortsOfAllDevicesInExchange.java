package io.omnipede.springbootrestapiboilerplate.core.usecase.exchange.getcapacity;

import io.omnipede.springbootrestapiboilerplate.core.entity.BroadbandAccessDevice;

import java.util.List;

public interface GetAvailablePortsOfAllDevicesInExchange {
    List<BroadbandAccessDevice> getAvailablePortsOfAllDevicesInExchange(String exchangeCode);
}
