package io.omnipede.dataprovider.jpa.exchange;

import io.omnipede.dataprovider.jpa.exchange.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<ExchangeEntity, Long> {
    Optional<ExchangeEntity> findByCode(String code);
    Long countByCode(String code);
}
