package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.MemberSignin;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.ProductRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.ProductService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.PurchaseService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;

import java.util.HashMap;
import java.util.Map;

import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    final ObjectMapper mapper = new ObjectMapper();

    /**
     * 회원가입 테스트
     */
    @Test
    public void signin() throws Exception {
        final String endPoint = "/member/signin";

        // Body to request
        Map<String, Object> requestBodyDTO = new HashMap<>();
        requestBodyDTO.put("username", "김길동");
        String bodyString = mapper.writeValueAsString(requestBodyDTO);
        // Call api
        mockMvc.perform(post(endPoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andDo(document("member-signin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").description("회원가입하는 멤버 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("요청 수행 성공")
                        )
                ));
    }

    /**
     * 회원 정보보기 테스트
     */
    @Test
    public void profile() throws Exception {
        final String endPoint = "/member/{id}";

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
                .andDo(document("member-profile",
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
}