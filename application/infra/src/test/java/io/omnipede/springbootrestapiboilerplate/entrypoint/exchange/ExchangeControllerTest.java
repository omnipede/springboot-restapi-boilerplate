package io.omnipede.springbootrestapiboilerplate.entrypoint.exchange;

import io.omnipede.core.entity.Capacity;
import io.omnipede.core.usecase.exchange.getcapacity.ExchangeNotFoundException;
import io.omnipede.core.usecase.exchange.getcapacity.GetCapacityForExchangeUseCase;
import io.omnipede.springbootrestapiboilerplate.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeControllerTest {

    private static final String EXCHANGE_CODE = "exchangeCode";

    private GetCapacityForExchangeUseCase getCapacityForExchangeUseCase = mock(GetCapacityForExchangeUseCase.class);

    private ExchangeController controller = new ExchangeController(getCapacityForExchangeUseCase);

    @Test
    public void returnsTheCapacityForAnExchange() throws Exception {
        givenThereIsCapacityForAnExchange();

        CapacityDTO dto = controller.getCapacity(EXCHANGE_CODE);

        assertThat(dto.isHasADSLCapacity()).isTrue();
        assertThat(dto.isHasFibreCapacity()).isTrue();
    }

    @Test
    public void errorWhenDeviceIsNotFound() throws Exception {
        givenAnExchangeThatDoesNotExist();

        try {
            controller.getCapacity(EXCHANGE_CODE);
            fail("Exception should be thrown");
        } catch(BusinessException e) {
            assertThat(e.getErrorCode().getStatus()).isEqualTo(404);
            assertThat(e.getErrorCode().getCode()).isEqualTo(40401);
        }
    }

    private void givenThereIsCapacityForAnExchange() {
        when(getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE)).thenReturn(new Capacity(true, true));
    }

    private void givenAnExchangeThatDoesNotExist() {
        when(getCapacityForExchangeUseCase.getCapacity(EXCHANGE_CODE)).thenThrow(new ExchangeNotFoundException());
    }
}