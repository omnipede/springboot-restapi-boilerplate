package io.omnipede.core.usecase.broadbandaccessdevice.getdetails;

import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.entity.DeviceType;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBroadbandAccessDeviceDetailsUseCaseTest {

    GetDeviceDetails getDeviceDetails = mock(GetDeviceDetails.class);
    GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase = new GetBroadbandAccessDeviceDetailsUseCase(getDeviceDetails);

    @Test
    public void returnsDeviceDetails() throws Exception {
        BroadbandAccessDevice expectedDevice = givenDeviceIsFound();

        BroadbandAccessDevice actualDevice = getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails("hostname1");

        assertThat(actualDevice).isEqualTo(expectedDevice);
    }

    @Test
    public void errorWhenDeviceIsNotFound() throws Exception {
        givenDeviceIsNotFound();

        assertThatExceptionOfType(DeviceNotFoundException.class).isThrownBy(() -> {
            getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails("hostname1");
        });
    }

    private BroadbandAccessDevice givenDeviceIsFound() {
        BroadbandAccessDevice expectedDevice = new BroadbandAccessDevice("hostname1", "serialNumber", DeviceType.ADSL);
        when(getDeviceDetails.getDetails("hostname1")).thenReturn(expectedDevice);
        return expectedDevice;
    }

    private void givenDeviceIsNotFound() {
        when(getDeviceDetails.getDetails(anyString())).thenReturn(null);
    }
}