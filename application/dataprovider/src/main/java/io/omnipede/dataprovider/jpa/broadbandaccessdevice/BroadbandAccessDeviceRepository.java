package io.omnipede.dataprovider.jpa.broadbandaccessdevice;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BroadbandAccessDeviceRepository extends CrudRepository<BroadbandAccessDeviceEntity, Long> {
    Optional<BroadbandAccessDeviceEntity> findByHostname(String hostname);
    List<BroadbandAccessDeviceEntity> findAllByExchangeEntity(ExchangeEntity exchangeEntity);
}