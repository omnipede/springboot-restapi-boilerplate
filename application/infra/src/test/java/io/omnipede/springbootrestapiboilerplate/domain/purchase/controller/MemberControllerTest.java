package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberProductRepository memberProductRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 회원 정보 보기 테스트
     */
    @Test
    public void profile() throws Exception {
        final String endPoint = "/api/v1/member/{id}";

        // 테스트용 데이터 추가
        Member member = memberRepository.save(new Member("김길동"));
        Product product = productRepository.save(new Product("냉장고"));
        Product product2 = productRepository.save(new Product("청소기"));

        memberProductRepository.save(new MemberProduct(member, product));
        memberProductRepository.save(new MemberProduct(member, product2));

        // Call API
        mockMvc.perform(RestDocumentationRequestBuilders.get(endPoint, member.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.purchased").isArray())
                .andDo(document("member/profile",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("멤버 아이디"),
                                fieldWithPath("username").description("멤버 이름"),
                                fieldWithPath("purchased").description("멤버가 구입한 상품 목록"),
                                fieldWithPath("purchased[].id").description("상품 아이디"),
                                fieldWithPath("purchased[].name").description("상품 이름"),
                                fieldWithPath("purchased[].updatedAt").description("상품 정보 업데이트 시각"),
                                fieldWithPath("purchased[].createdAt").description("상품 정보 생성 시각")
                        )
                ));
    }

    /**
     * 회원정보 보기 - 회원 정보가 없을 때
     */
    @Test
    public void profile_NotFound() throws Exception {
        final String endPoint = "/api/v1/member/{id}";

        // Call API
        mockMvc.perform(RestDocumentationRequestBuilders.get(endPoint, "777")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(document("member/profile-NotFound",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("회원 아이디")
                ),
                responseFields(
                        fieldWithPath("status").description("Http status"),
                        fieldWithPath("code").description("Error code"),
                        fieldWithPath("message").description("Error message")
                )));
    }
}