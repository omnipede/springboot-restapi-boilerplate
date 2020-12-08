package io.omnipede.core.usecase.broadbandaccessdevice.getdetails;

import io.omnipede.core.entity.BroadbandAccessDevice;

public class GetBroadbandAccessDeviceDetailsUseCase {

    private final GetDeviceDetails getDeviceDetails;

    public GetBroadbandAccessDeviceDetailsUseCase(GetDeviceDetails getDeviceDetails) {
        this.getDeviceDetails = getDeviceDetails;
    }

    public BroadbandAccessDevice getDeviceDetails(String hostname) {
        BroadbandAccessDevice device = getDeviceDetails.getDetails(hostname);

        if (device == null)
            throw new DeviceNotFoundException();

        return device;
    }
}
