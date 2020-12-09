package io.omnipede.rest.entrypoint.bean;

import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetBroadbandAccessDeviceDetailsUseCase;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetDeviceDetails;
import io.omnipede.core.usecase.exchange.getcapacity.DoesExchangeExist;
import io.omnipede.core.usecase.exchange.getcapacity.GetAvailablePortsOfAllDevicesInExchange;
import io.omnipede.core.usecase.exchange.getcapacity.GetCapacityForExchangeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase(GetDeviceDetails getDeviceDetails) {
        return new GetBroadbandAccessDeviceDetailsUseCase(getDeviceDetails);
    }

    @Bean
    public GetCapacityForExchangeUseCase getCapacityForExchangeUseCase(DoesExchangeExist doesExchangeExist, GetAvailablePortsOfAllDevicesInExchange getAvailablePortsOfAllDevicesInExchange) {
        return new GetCapacityForExchangeUseCase(doesExchangeExist, getAvailablePortsOfAllDevicesInExchange);
    }
}
