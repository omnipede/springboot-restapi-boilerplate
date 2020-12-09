package io.omnipede.rest.entrypoint.broadbandaccessdevice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BroadbandAccessDeviceDTO {
    private final String exchangeCode;
    private final String hostname;
    private final String serialNumber;
    private final String type;
}
