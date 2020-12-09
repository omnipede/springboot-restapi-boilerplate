package io.omnipede.rest.domain.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.rest.domain.purchase.entity.Member;
import io.omnipede.rest.domain.purchase.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.omnipede.rest.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.rest.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PurchaseControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Product 추가 테스트
     * @throws Exception
     */
    @Test
    public void purchase() throws Exception {
        final String endPoint = "/api/v1/purchase";

        // 테스트용 데이터 추가
        Member member = memberRepository.save(new Member("김길동"));

        // Body to request
        Map<String, Object> requestBodyDTO = new HashMap<>();
        requestBodyDTO.put("member_id", member.getId());
        requestBodyDTO.put("product_name", "냉장고");
        String bodyString = objectMapper.writeValueAsString(requestBodyDTO);

        // Call api
        mockMvc.perform(post(endPoint)
                    .header("test", "foo/bar")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document("purchase/add",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("test").description("Header documentation test").optional()
                        ),
                        requestFields(
                                fieldWithPath("member_id").description("상품 구입할 멤버 아이디"),
                                fieldWithPath("product_name").description("구입하는 상품 이름")
                        ),
                        responseFields(
                                fieldWithPath("status").description("요청 수행 성공")
                        )
                ));
    }

    /**
     * 회원 가입 - Bad request
     */
    @Test
    public void purchase_BadRequest() throws Exception {
        final String endPoint = "/api/v1/purchase";

        // Given
        List<Map<String, Object>> invalidRequestBodyList = new ArrayList<>();

        // No 'productName' field
        Map<String, Object> body = new HashMap<>();
        body.put("memberId", "1");
        invalidRequestBodyList.add(body);

        // No 'memberId' field
        body = new HashMap<>();
        body.put("productName", "냉장고");
        invalidRequestBodyList.add(body);

        // Call api for each invalid body
        invalidRequestBodyList.forEach((invalidRequestBody) -> {
            try {
                String content = objectMapper.writeValueAsString(invalidRequestBody);
                // When
                mockMvc.perform(post(endPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        // Then
                        .andExpect(status().isBadRequest())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.code").value(40000))
                        .andDo(document("purchase/add-BadRequest",
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("status").description("Http status code"),
                                        fieldWithPath("code").description("Error code"),
                                        fieldWithPath("message").description("Error message"),
                                        fieldWithPath("errors").description("Specific reasons of erorrs"),
                                        fieldWithPath("errors[].field").description("에러 발생한 필드"),
                                        fieldWithPath("errors[].value").description("클라이언트가 전송한 필드 값"),
                                        fieldWithPath("errors[].reason").description("에러 발생한 이유")
                                ))
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}