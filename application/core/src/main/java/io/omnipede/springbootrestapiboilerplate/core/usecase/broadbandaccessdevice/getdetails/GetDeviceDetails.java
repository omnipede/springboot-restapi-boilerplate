package io.omnipede.springbootrestapiboilerplate.core.usecase.broadbandaccessdevice.getdetails;

import io.omnipede.springbootrestapiboilerplate.core.entity.BroadbandAccessDevice;

public interface GetDeviceDetails {
    BroadbandAccessDevice getDetails(String hostname);
}
