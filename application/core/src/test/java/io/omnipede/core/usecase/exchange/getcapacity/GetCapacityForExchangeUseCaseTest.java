package io.omnipede.core.usecase.exchange.getcapacity;

import io.omnipede.core.entity.Capacity;
import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.entity.DeviceType;
import org.junit.Before;
import org.junit.Test;

import static io.omnipede.core.entity.DeviceType.ADSL;
import static io.omnipede.core.entity.DeviceType.FIBRE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCapacityForExchangeUseCaseTest {

    private static final String EXCHANGE_CODE = "exchangeCode";
    private static final int NO_PORTS = 0;

    DoesExchangeExist doesExchangeExist = mock(DoesExchangeExist.class);
    GetAvailablePortsOfAllDevicesInExchange getAvailablePortsOfAllDevicesInExchange = mock(GetAvailablePortsOfAllDevicesInExchange.class);

    GetCapacityForExchangeUseCase getCapacityForExchangeUseCase = new GetCapacityForExchangeUseCase(doesExchangeExist, getAvailablePortsOfAllDevicesInExchange);

    @Before
    public void setup() throws Exception {
        givenExchangeExists();
    }

    @Test
    public void noCapacityIfDevicesHaveNoPorts() throws Exception {
        // Given
        givenDevice(device(FIBRE, NO_PORTS), device(ADSL, NO_PORTS));
        // When
        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);
        // Then
        assertThat(capacity.hasAdslCapacity()).isFalse();
    }

    @Test
    public void hasCapacityIfAdslDeviceHasPorts() throws Exception {
        givenDevice(device(ADSL, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void hasOnlyAdslCapacityIfAdslDeviceHasPorts() throws Exception {
        givenDevice(device(ADSL, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isFalse();
    }

    @Test
    public void hasCapacityIfFibreDeviceHasPorts() throws Exception {
        givenDevice(device(FIBRE, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void hasOnlyFibreCapacityIfFibreDeivceHasPorts() throws Exception {
        givenDevice(device(FIBRE, 10));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isFalse();
    }

    @Test
    public void capacityJoinsAvailablePortsForAdslDevices() throws Exception {
        givenDevice(device(ADSL, 10), device(ADSL, NO_PORTS));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void capacityJoinsAvailablePortsForFibreDevices() throws Exception {
        givenDevice(device(FIBRE, 10), device(FIBRE, NO_PORTS));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void adslHasNoCapacityIfAvailablePortIsLessThan5() throws Exception {
        givenDevice(device(ADSL, 1), device(ADSL, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isFalse();
    }

    @Test
    public void fibreHasNoCapacityIfAvailablePortIsLessThan5() throws Exception {
        givenDevice(device(FIBRE, 1), device(FIBRE, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isFalse();
    }

    @Test
    public void adslHasCapacityIfAvailablePortIs5() throws Exception {
        givenDevice(device(ADSL, 2), device(ADSL, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasAdslCapacity()).isTrue();
    }

    @Test
    public void fibreHasCapacityIfAvailablePortIs5() throws Exception {
        givenDevice(device(FIBRE, 2), device(FIBRE, 3));

        Capacity capacity = getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);

        assertThat(capacity.hasFibreCapacity()).isTrue();
    }

    @Test
    public void errorWhenExchangeDoesNotExist() throws Exception {
        givenExchangeDoesNotExist();

        assertThatExceptionOfType(ExchangeNotFoundException.class).isThrownBy(() -> {
            getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE);
        });
    }

    private void givenExchangeExists() {
        when(doesExchangeExist.doesExchangeExist(EXCHANGE_CODE)).thenReturn(true);
    }

    private void givenDevice(BroadbandAccessDevice... broadbandAccessDevices) {
        when(getAvailablePortsOfAllDevicesInExchange.getAvailablePortsOfAllDevicesInExchange(EXCHANGE_CODE)).thenReturn(asList(broadbandAccessDevices));
    }

    private void givenExchangeDoesNotExist() {
        when(doesExchangeExist.doesExchangeExist(EXCHANGE_CODE)).thenReturn(false);
    }

    private BroadbandAccessDevice device(DeviceType deviceType, Integer availablePorts) {
        BroadbandAccessDevice broadbandAccessDevice = new BroadbandAccessDevice("hostname", "serial", deviceType);
        broadbandAccessDevice.setAvailablePorts(availablePorts);
        return broadbandAccessDevice;
    }
}