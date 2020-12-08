package io.omnipede.dataprovider.database.exchange;

import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeEntity;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeDatabaseDataProviderTest {

    private ExchangeRepository exchangeRepository = mock(ExchangeRepository.class);

    private ExchangeDatabaseDataProvider exchangeDatabaseDataProvider = new ExchangeDatabaseDataProvider(exchangeRepository);

    @Test
    public void falseWhenExchangeDoesNotExist() throws Exception {
        givenExchangeDoesNotExists();

        boolean result = exchangeDatabaseDataProvider.doesExchangeExist("exchangeCode");

        assertThat(result).isFalse();
    }

    @Test
    public void trueWhenExchangeExists() throws Exception {
        givenExchangeExists();

        boolean result = exchangeDatabaseDataProvider.doesExchangeExist("exchangeCode");

        assertThat(result).isTrue();
    }

    private void givenExchangeExists() {
        when(exchangeRepository.countByCode(anyString())).thenReturn(2L);
    }

    private void givenExchangeDoesNotExists() {
        when(exchangeRepository.countByCode(anyString())).thenReturn(0L);
    }
}