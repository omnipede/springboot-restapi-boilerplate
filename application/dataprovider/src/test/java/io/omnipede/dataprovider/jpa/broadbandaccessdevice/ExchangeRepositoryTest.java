package io.omnipede.dataprovider.jpa.broadbandaccessdevice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class ExchangeRepositoryTest {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Test
    public void repositoryIsNotNull() throws Exception {
        assertThat(exchangeRepository).isNotNull();
    }
}
