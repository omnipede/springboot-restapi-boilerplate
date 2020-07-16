package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.MemberSignin;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
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

import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    final ObjectMapper mapper = new ObjectMapper();

    /**
     * 회원가입 테스트
     */
    @Test
    public void signin() throws Exception {
        final String endPoint = "/member/signin";

        // Body to request
        MemberSignin body = new MemberSignin("서현규");
        String bodyString = mapper.writeValueAsString(body);
        // Call api
        mockMvc.perform(post(endPoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("member-signin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").description("회원가입하는 멤버 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("멤버 아이디"),
                                fieldWithPath("username").description("멤버 이름"),
                                fieldWithPath("createdAt").description("생성한 시각"),
                                fieldWithPath("updatedAt").description("최종 업데이트 시각"),
                                fieldWithPath("memberProducts").description("멤버가 구입한 상품 목록")
                        )
                ));
    }

    /**
     * 회원 정보보기 테스트
     */
    @Test
    public void profile() throws Exception {
        final String endPoint = "/member/{id}";

        // Call API
        mockMvc.perform(RestDocumentationRequestBuilders.get(endPoint, "1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("member-profile",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("멤버 아이디"),
                                fieldWithPath("username").description("멤버 이름"),
                                fieldWithPath("createdAt").description("생성한 시각"),
                                fieldWithPath("updatedAt").description("최종 업데이트 시각"),
                                fieldWithPath("memberProducts").description("멤버가 구입한 상품 목록")
                        )
                ));
    }

    @Test
    public void profileNotFound() throws Exception {

    }
}