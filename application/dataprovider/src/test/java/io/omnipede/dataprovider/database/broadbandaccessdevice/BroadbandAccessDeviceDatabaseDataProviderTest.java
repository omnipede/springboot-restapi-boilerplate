package io.omnipede.dataprovider.database.broadbandaccessdevice;

import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceEntity;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceRepository;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeEntity;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeRepository;
import io.omnipede.springbootrestapiboilerplate.core.entity.BroadbandAccessDevice;
import io.omnipede.springbootrestapiboilerplate.core.entity.DeviceType;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BroadbandAccessDeviceDatabaseDataProviderTest {

    private static final String HOST_NAME = "hostname";

    private BroadbandAccessDeviceRepository broadbandAccessDeviceRepository = mock(BroadbandAccessDeviceRepository.class);
    private ExchangeRepository exchangeRepository = mock(ExchangeRepository.class);

    private BroadbandAccessDeviceDatabaseDataProvider broadbandAccessDeviceDatabaseDataProvider
            = new BroadbandAccessDeviceDatabaseDataProvider(broadbandAccessDeviceRepository, exchangeRepository);

    @Test
    public void returnsTheDetailsOfADevice() throws Exception {
        givenThereIsADevice(device("hostname", "serialNumber", DeviceType.ADSL, 123,
                exchange("exchangeCode", "exchangeName", "exchangePostCode")));

        BroadbandAccessDevice device = broadbandAccessDeviceDatabaseDataProvider.getDetails(HOST_NAME);

        assertThat(device).isNotNull();
        assertThat(device.getHostname()).isEqualTo(HOST_NAME);
        assertThat(device.getSerialNumber()).isEqualTo("serialNumber");
        assertThat(device.getAvailablePorts()).isEqualTo(123);
        assertThat(device.getExchange().getCode()).isEqualTo("exchangeCode");
        assertThat(device.getExchange().getName()).isEqualTo("exchangeName");
        assertThat(device.getExchange().getPostCode()).isEqualTo("exchangePostCode");
    }

    @Test
    public void returnsNullWhenThereAreNoDevices() throws Exception {
        givenThereIsntADevice();

        BroadbandAccessDevice device = broadbandAccessDeviceDatabaseDataProvider.getDetails(HOST_NAME);

        assertThat(device).isNull();
    }

    @Test
    public void returnsAvailablePortsOfAllDevicesInAnExchange() throws Exception {
        ExchangeEntity exchange = exchange("exchangeCode", "exchangeName", "exchangePostCode");
        givenThereIsAnExchange(
                exchange,
                device("hostname", "serialNumber", DeviceType.ADSL, 123, exchange),
                device("hostname", "serialNumber", DeviceType.FIBRE, 345, exchange)
        );

        List<BroadbandAccessDevice> devices = broadbandAccessDeviceDatabaseDataProvider.getAvailablePortsOfAllDevicesInExchange("exchangeCode");

        assertThat(devices.size()).isEqualTo(2);
        assertThat(devices.get(0).getDeviceType()).isEqualTo(DeviceType.ADSL);
        assertThat(devices.get(0).getAvailablePorts()).isEqualTo(123);
        assertThat(devices.get(1).getDeviceType()).isEqualTo(DeviceType.FIBRE);
        assertThat(devices.get(1).getAvailablePorts()).isEqualTo(345);
    }

    private void givenThereIsntADevice() {
        when(broadbandAccessDeviceRepository.findByHostname(HOST_NAME)).thenReturn(Optional.empty());
    }

    private void givenThereIsADevice(BroadbandAccessDeviceEntity device) {
        when(broadbandAccessDeviceRepository.findByHostname(HOST_NAME)).thenReturn(Optional.of(device));
    }

    private void givenThereIsAnExchange(ExchangeEntity exchange, BroadbandAccessDeviceEntity... devices) {
        when(exchangeRepository.findByCode(exchange.getCode()))
                .thenReturn(Optional.of(exchange));
        when(broadbandAccessDeviceRepository.findAllByExchangeEntity(exchange))
                .thenReturn(asList(devices));
    }

    private ExchangeEntity exchange(String exchangeCode, String exchangeName, String exchangePostCode) {
        ExchangeEntity exchangeEntity = new ExchangeEntity();
        exchangeEntity.setCode(exchangeCode);
        exchangeEntity.setName(exchangeName);
        exchangeEntity.setPostcode(exchangePostCode);
        return exchangeEntity;
    }

    private BroadbandAccessDeviceEntity device(String hostname, String serialNumber, DeviceType deviceType, Integer availablePorts, ExchangeEntity exchangeEntity) {
        BroadbandAccessDeviceEntity device = new BroadbandAccessDeviceEntity();
        device.setHostname(hostname);
        device.setSerialNumber(serialNumber);
        device.setDeviceType(deviceType);
        device.setAvailablePorts(availablePorts);
        device.setExchangeEntity(exchangeEntity);
        return device;
    }
}