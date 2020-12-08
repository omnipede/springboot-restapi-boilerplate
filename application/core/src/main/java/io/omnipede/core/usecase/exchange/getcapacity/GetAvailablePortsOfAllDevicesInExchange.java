package io.omnipede.core.usecase.exchange.getcapacity;

import io.omnipede.core.entity.BroadbandAccessDevice;

import java.util.List;

public interface GetAvailablePortsOfAllDevicesInExchange {
    List<BroadbandAccessDevice> getAvailablePortsOfAllDevicesInExchange(String exchangeCode);
}
