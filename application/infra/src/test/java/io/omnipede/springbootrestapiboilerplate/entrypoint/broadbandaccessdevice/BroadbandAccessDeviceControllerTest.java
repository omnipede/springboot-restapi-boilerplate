package io.omnipede.springbootrestapiboilerplate.entrypoint.broadbandaccessdevice;

import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.entity.DeviceType;
import io.omnipede.core.entity.Exchange;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetBroadbandAccessDeviceDetailsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BroadbandAccessDeviceControllerTest {

    private GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase
            = mock(GetBroadbandAccessDeviceDetailsUseCase.class);

    private BroadbandAccessDeviceController controller
            = new BroadbandAccessDeviceController(getBroadbandAccessDeviceDetailsUseCase);

    @Test
    public void returnBroadbandAccessDeviceDTO() throws Exception {
        BroadbandAccessDevice device = deviceDetail();
        givenABroadbandAccessDeviceDetails(device);

        BroadbandAccessDeviceDTO dto = controller.getDetails(device.getHostname());

        assertThat(dto.getHostname()).isEqualTo(device.getHostname());
        assertThat(dto.getExchangeCode()).isEqualTo(device.getExchange().getCode());
        assertThat(dto.getSerialNumber()).isEqualTo(device.getSerialNumber());
        assertThat(dto.getType()).isEqualTo(device.getDeviceType().name());
    }

    private void givenABroadbandAccessDeviceDetails(BroadbandAccessDevice device) {
        String hostname = device.getHostname();
        when(getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails(hostname))
            .thenReturn(device);
    }

    private BroadbandAccessDevice deviceDetail() {
        BroadbandAccessDevice device = new BroadbandAccessDevice("hostname", "serialNumber", DeviceType.ADSL);
        Exchange exchange = new Exchange("code", "name", "postCode");
        device.setAvailablePorts(123);
        device.setExchange(exchange);
        return device;
    }
}