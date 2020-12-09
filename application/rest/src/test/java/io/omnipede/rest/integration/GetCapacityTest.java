package io.omnipede.rest.integration;

import io.omnipede.core.entity.DeviceType;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceEntity;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceRepository;
import io.omnipede.dataprovider.jpa.exchange.ExchangeEntity;
import io.omnipede.dataprovider.jpa.exchange.ExchangeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static io.omnipede.rest.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.rest.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("test")
class GetCapacityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BroadbandAccessDeviceRepository broadbandAccessDeviceRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Test
    public void getCapacity() throws Exception {
        final String endpoint = "/api/v1/exchange/{exchangeCode}/capacity";

        // Given
        ExchangeEntity exchange = exchange();
        device(DeviceType.ADSL, 1, exchange);
        device(DeviceType.FIBRE, 6, exchange);

        // When
        mockMvc.perform(RestDocumentationRequestBuilders.get(endpoint, exchange.getCode())
                .contentType(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.hasADSLCapacity").value(false))
        .andExpect(jsonPath("$.hasFibreCapacity").value(true))

        // Create rest document
        .andDo(document("exchange/capacity",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("exchangeCode").description("Exchange code")
                ),
                responseFields(
                        fieldWithPath("hasADSLCapacity").description("ADSL capacity 여유 여부"),
                        fieldWithPath("hasFibreCapacity").description("FIBRE capacity 여유 여부")
                )));
    }

    private BroadbandAccessDeviceEntity device(DeviceType deviceType, Integer availablePorts, ExchangeEntity exchange) {
        BroadbandAccessDeviceEntity device = new BroadbandAccessDeviceEntity();
        device.setAvailablePorts(availablePorts);
        device.setDeviceType(deviceType);
        device.setSerialNumber("serialNumber");
        device.setHostname("hostname");
        device.setExchangeEntity(exchange);
        return broadbandAccessDeviceRepository.save(device);
    }

    private ExchangeEntity exchange() {
        ExchangeEntity exchange = new ExchangeEntity();
        exchange.setCode("code");
        exchange.setName("name");
        exchange.setPostcode("postcode");
        return exchangeRepository.save(exchange);
    }
}
