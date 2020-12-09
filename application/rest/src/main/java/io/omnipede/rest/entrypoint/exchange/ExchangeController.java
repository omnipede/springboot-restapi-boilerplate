package io.omnipede.rest.entrypoint.exchange;

import io.omnipede.core.entity.Capacity;
import io.omnipede.core.usecase.exchange.getcapacity.ExchangeNotFoundException;
import io.omnipede.core.usecase.exchange.getcapacity.GetCapacityForExchangeUseCase;
import io.omnipede.rest.global.exception.BusinessException;
import io.omnipede.rest.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExchangeController {

    private GetCapacityForExchangeUseCase getCapacityForExchangeUseCase;

    @Autowired
    public ExchangeController (GetCapacityForExchangeUseCase getCapacityForExchangeUseCase) {
        this.getCapacityForExchangeUseCase = getCapacityForExchangeUseCase;
    }

    @GetMapping(value = "/exchange/{exchangeCode}/capacity", headers="accept=application/json")
    public CapacityDTO getCapacity(@PathVariable String exchangeCode) {
        try {
            Capacity capacity = getCapacityForExchangeUseCase.getCapacity(exchangeCode);
            return toDTO(capacity);
        } catch (ExchangeNotFoundException e) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
        }
    }

    private CapacityDTO toDTO(Capacity capacity) {
        return new CapacityDTO(capacity.hasAdslCapacity(), capacity.hasFibreCapacity());
    }
}
