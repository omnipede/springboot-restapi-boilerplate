package io.omnipede.springbootrestapiboilerplate.glue;

import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetBroadbandAccessDeviceDetailsUseCase;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetDeviceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase(GetDeviceDetails getDeviceDetails) {
        return new GetBroadbandAccessDeviceDetailsUseCase(getDeviceDetails);
    }
}
