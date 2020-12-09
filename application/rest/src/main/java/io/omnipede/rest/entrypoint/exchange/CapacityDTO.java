package io.omnipede.rest.entrypoint.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CapacityDTO {
    private final boolean hasADSLCapacity;
    private final boolean hasFibreCapacity;
}
