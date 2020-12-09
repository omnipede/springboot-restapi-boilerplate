package io.omnipede.dataprovider.database.broadbandaccessdevice;

import io.omnipede.dataprovider.jpa.exchange.ExchangeEntity;
import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.entity.DeviceType;
import io.omnipede.core.entity.Exchange;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetDeviceDetails;
import io.omnipede.core.usecase.exchange.getcapacity.GetAvailablePortsOfAllDevicesInExchange;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceEntity;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceRepository;
import io.omnipede.dataprovider.jpa.exchange.ExchangeRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BroadbandAccessDeviceDatabaseDataProvider implements GetDeviceDetails, GetAvailablePortsOfAllDevicesInExchange {

    private BroadbandAccessDeviceRepository broadbandAccessDeviceRepository;
    private ExchangeRepository exchangeRepository;

    public BroadbandAccessDeviceDatabaseDataProvider(BroadbandAccessDeviceRepository broadbandAccessDeviceRepository, ExchangeRepository exchangeRepository) {
        this.broadbandAccessDeviceRepository = broadbandAccessDeviceRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public BroadbandAccessDevice getDetails(String hostname) {
        // Find by hostname
        BroadbandAccessDeviceEntity broadbandAccessDeviceEntity = broadbandAccessDeviceRepository.findByHostname(hostname).orElse(null);
        if (broadbandAccessDeviceEntity == null)
            return null;

        // Serialize jpa entity to domain entity
        ExchangeEntity exchangeEntity = broadbandAccessDeviceEntity.getExchangeEntity();
        String code = exchangeEntity.getCode();
        String name = exchangeEntity.getName();
        String postcode = exchangeEntity.getPostcode();
        Exchange exchange = new Exchange(code, name, postcode);

        String serialNumber = broadbandAccessDeviceEntity.getSerialNumber();
        DeviceType deviceType = broadbandAccessDeviceEntity.getDeviceType();
        Integer availablePorts = broadbandAccessDeviceEntity.getAvailablePorts();
        BroadbandAccessDevice broadbandAccessDevice = new BroadbandAccessDevice(hostname, serialNumber, deviceType);
        broadbandAccessDevice.setAvailablePorts(availablePorts);
        broadbandAccessDevice.setExchange(exchange);

        return broadbandAccessDevice;
    }

    @Override
    public List<BroadbandAccessDevice> getAvailablePortsOfAllDevicesInExchange(String exchangeCode) {
        // Find by exchange code
        ExchangeEntity exchangeEntity = exchangeRepository.findByCode(exchangeCode).orElse(null);
        if(exchangeEntity == null)
            return Collections.emptyList();

        // Get list of broad band access device
        List<BroadbandAccessDeviceEntity> broadbandAccessDeviceEntities = broadbandAccessDeviceRepository.findAllByExchangeEntity(exchangeEntity);

        return broadbandAccessDeviceEntities.stream().map(broadbandAccessDeviceEntity -> {
            String hostname = broadbandAccessDeviceEntity.getHostname();
            String serialNumber = broadbandAccessDeviceEntity.getSerialNumber();
            DeviceType deviceType = broadbandAccessDeviceEntity.getDeviceType();
            Integer availablePorts = broadbandAccessDeviceEntity.getAvailablePorts();
            BroadbandAccessDevice device = new BroadbandAccessDevice(hostname, serialNumber, deviceType);
            device.setAvailablePorts(availablePorts);
            return device;
        }).collect(Collectors.toList());
    }
}
