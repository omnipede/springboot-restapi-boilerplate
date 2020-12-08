package io.omnipede.core.usecase.broadbandaccessdevice.getdetails;

import io.omnipede.core.entity.BroadbandAccessDevice;

public interface GetDeviceDetails {
    BroadbandAccessDevice getDetails(String hostname);
}
