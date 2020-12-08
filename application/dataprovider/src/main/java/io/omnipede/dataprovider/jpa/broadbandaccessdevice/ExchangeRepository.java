package io.omnipede.dataprovider.jpa.broadbandaccessdevice;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExchangeRepository extends CrudRepository<ExchangeEntity, Long> {
    Optional<ExchangeEntity> findByCode(String code);
}
