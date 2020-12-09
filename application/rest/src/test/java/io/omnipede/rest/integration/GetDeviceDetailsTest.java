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
class GetDeviceDetailsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BroadbandAccessDeviceRepository broadbandAccessDeviceRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Test
    public void getDeviceDetails() throws Exception {
        final String endpoint = "/api/v1/broadbandaccessdevice/{hostname}";
        // Given
        BroadbandAccessDeviceEntity device = device(exchange());

        // When
        mockMvc.perform(RestDocumentationRequestBuilders.get(endpoint, device.getHostname())
                .contentType(MediaType.APPLICATION_JSON))

        // Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.exchangeCode").isString())
        .andExpect(jsonPath("$.hostname").isString())
        .andExpect(jsonPath("$.serialNumber").isString())
        .andExpect(jsonPath("$.type").isString())

        // Create rest document
        .andDo(document("broadbandaccessdevice/details",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("hostname").description("Host name of device")
                ),
                responseFields(
                        fieldWithPath("exchangeCode").description("Exchange code"),
                        fieldWithPath("hostname").description("Host name"),
                        fieldWithPath("serialNumber").description("Serial number"),
                        fieldWithPath("type").description("Device type. ADSL or FIBRE")
                )));
    }

    private BroadbandAccessDeviceEntity device(ExchangeEntity exchange) {
        BroadbandAccessDeviceEntity device = new BroadbandAccessDeviceEntity();
        device.setAvailablePorts(123);
        device.setDeviceType(DeviceType.ADSL);
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
