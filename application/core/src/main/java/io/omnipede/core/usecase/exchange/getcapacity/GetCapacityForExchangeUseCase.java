package io.omnipede.core.usecase.exchange.getcapacity;

import io.omnipede.core.entity.Capacity;
import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.entity.DeviceType;

import java.util.List;

public class GetCapacityForExchangeUseCase {

    private static final int MINIMUM_NUMBER_OF_PORTS = 5;

    private DoesExchangeExist doesExchangeExist;
    private GetAvailablePortsOfAllDevicesInExchange getAvailablePortsOfAllDevicesInExchange;

    public GetCapacityForExchangeUseCase(DoesExchangeExist doesExchangeExist, GetAvailablePortsOfAllDevicesInExchange getAvailablePortsOfAllDevicesInExchange) {
        this.doesExchangeExist = doesExchangeExist;
        this.getAvailablePortsOfAllDevicesInExchange = getAvailablePortsOfAllDevicesInExchange;
    }

    public Capacity getCapacity(String exchangeCode) {
        failIfExchangeDoesNotExist(exchangeCode);
        List<BroadbandAccessDevice> devices = getAvailablePortsOfAllDevicesInExchange.getAvailablePortsOfAllDevicesInExchange(exchangeCode);
        boolean hasAdslCapacity = hasCapacityFor(devices, DeviceType.ADSL);
        boolean hasFibreCapacity = hasCapacityFor(devices, DeviceType.FIBRE);
        return new Capacity(hasAdslCapacity, hasFibreCapacity);
    }

    private void failIfExchangeDoesNotExist(String exchangeCode) {
        boolean exchangeExists = doesExchangeExist.doesExchangeExist(exchangeCode);
        if (!exchangeExists)
            throw new ExchangeNotFoundException();
    }

    private boolean hasCapacityFor(List<BroadbandAccessDevice> devices, DeviceType deviceType) {
        int availablePorts = countAvailablePortsForType(devices, deviceType);
        return availablePorts >= MINIMUM_NUMBER_OF_PORTS;
    }

    private int countAvailablePortsForType(List<BroadbandAccessDevice> devices, DeviceType deviceType) {
        Integer availablePorts = 0;
        for (BroadbandAccessDevice device: devices) {
            if (device.getDeviceType() == deviceType)
                availablePorts += device.getAvailablePorts();
        }
        return availablePorts;
    }
}
