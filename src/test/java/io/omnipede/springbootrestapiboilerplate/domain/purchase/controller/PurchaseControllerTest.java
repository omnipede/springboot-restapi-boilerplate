package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
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

import java.util.HashMap;
import java.util.Map;

import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentResponse;
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

    final ObjectMapper mapper = new ObjectMapper();

    /**
     * Product 추가 테스트
     * @throws Exception
     */
    @Test
    public void purchase() throws Exception {
        final String endPoint = "/purchase";

        // 테스트용 데이터 추가
        Member member = memberRepository.save(new Member("김길동"));

        // Body to request
        Map<String, Object> requestBodyDTO = new HashMap<>();
        requestBodyDTO.put("memberId", member.getId());
        requestBodyDTO.put("productName", "냉장고");
        String bodyString = mapper.writeValueAsString(requestBodyDTO);

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
                                fieldWithPath("memberId").description("상품 구입할 멤버 아이디"),
                                fieldWithPath("productName").description("구입하는 상품 이름")
                        ),
                        responseFields(
                                fieldWithPath("status").description("요청 수행 성공")
                        )
                ));
    }
}