package io.omnipede.springbootrestapiboilerplate.glue;

import io.omnipede.dataprovider.database.broadbandaccessdevice.BroadbandAccessDeviceDatabaseDataProvider;
import io.omnipede.dataprovider.database.exchange.ExchangeDatabaseDataProvider;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceRepository;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseDataProviderConfiguration {

    @Bean
    public BroadbandAccessDeviceDatabaseDataProvider broadbandAccessDeviceDatabaseDataProvider(BroadbandAccessDeviceRepository broadbandAccessDeviceRepository, ExchangeRepository exchangeRepository) {
        return new BroadbandAccessDeviceDatabaseDataProvider(broadbandAccessDeviceRepository, exchangeRepository);
    }

    @Bean
    public ExchangeDatabaseDataProvider exchangeDatabaseDataProvider(ExchangeRepository exchangeRepository) {
        return new ExchangeDatabaseDataProvider(exchangeRepository);
    }
}
