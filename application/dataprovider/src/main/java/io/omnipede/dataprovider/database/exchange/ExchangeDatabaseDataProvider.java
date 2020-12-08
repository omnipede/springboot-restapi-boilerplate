package io.omnipede.dataprovider.database.exchange;

import io.omnipede.core.usecase.exchange.getcapacity.DoesExchangeExist;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeRepository;

public class ExchangeDatabaseDataProvider implements DoesExchangeExist {

    private ExchangeRepository exchangeRepository;

    public ExchangeDatabaseDataProvider(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public boolean doesExchangeExist(String exchangeCode) {
        Long count = exchangeRepository.countByCode(exchangeCode);
        if(count == 0)
            return false;
        return true;
    }
}
