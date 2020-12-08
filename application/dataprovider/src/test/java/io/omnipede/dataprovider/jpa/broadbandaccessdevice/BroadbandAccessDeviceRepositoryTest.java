package io.omnipede.dataprovider.jpa.broadbandaccessdevice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BroadbandAccessDeviceRepositoryTest {

    @Autowired
    private BroadbandAccessDeviceRepository broadbandAccessDeviceRepository;

    @Test
    public void repositoryIsNotNull() throws Exception {
        assertThat(broadbandAccessDeviceRepository).isNotNull();
    }
}
