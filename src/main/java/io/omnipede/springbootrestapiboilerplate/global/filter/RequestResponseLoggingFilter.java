package io.omnipede.springbootrestapiboilerplate.global.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 모든 request와 response 를 logging 하는 필터
 * @see "https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl"
 */
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int maxContentLength = 1024;
    private static final String charset = "UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 시간 측정 시작
        long startTime = System.currentTimeMillis();
        // Request body 의 input stream 을 읽으면 input stream 이 유실되는 문제를 해결하기 위해 다음과 같이 wrapping 한다.
        // @see "https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl"
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Continue
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // Response 까지 걸린 시간
        long duration = System.currentTimeMillis() - startTime;

        Map<String, Object> reqResLoggingDTO = new LinkedHashMap<>();
        // Format request
        Map<String, Object> formattedRequest = formatRequest(wrappedRequest);
        reqResLoggingDTO.put("req", formattedRequest);
        // Request ~ response duration (ms)
        reqResLoggingDTO.put("duration", String.valueOf(duration).concat("ms"));
        // Format response
        Map<String, Object> formattedResponse = formatResponse(wrappedResponse);
        reqResLoggingDTO.put("res", formattedResponse);
        // Log request & response
        logger.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqResLoggingDTO));
        // (!) IMPORTANT: copy content of response back into original response
        wrappedResponse.copyBodyToResponse();
    }

    /**
     * Request 에서 필요한 정보를 추출해 serializable 한 객체를 생성하는 메소드
     * @param request Serialize 할 http request
     * @return formatted request
     */
    private Map<String, Object> formatRequest (ContentCachingRequestWrapper request) {
        Map<String, Object> formatted = new LinkedHashMap<>();

        // Get & set method, URI
        formatted.put("method", request.getMethod());
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query != null) {
            uri += "?" + query;
        }
        formatted.put("uri", uri);
        // Get & set request headers
        Map<String, Object> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        formatted.put("headers", headers);
        // Get & set request body content
        byte[] contentAsByteArray = request.getContentAsByteArray();
        String contentString = getContentAsString(contentAsByteArray, maxContentLength, charset);
        try {
            // Content string 을 json map object 로 변환
            Map<String, Object> content = objectMapper.readValue(contentString, new TypeReference<Map<String, Object>>() {});
            formatted.put("body", content);
        } catch(Exception e) {
            formatted.put("body", contentString);
        }

        return formatted;
    }

    /**
     * Response 에서 필요한 정보를 추출해 serializable 한 객체를 생성하는 메소드
     * @param response Serialize 할 http response
     * @return formatted response
     */
    private Map<String, Object> formatResponse(ContentCachingResponseWrapper response) {
        Map<String, Object> formatted = new LinkedHashMap<>();

        // Get & set status code
        int httpStatus = response.getStatus();
        formatted.put("status", httpStatus);

        // Get & set headers
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        headerNames.forEach((headerName) -> {
            headers.put(headerName, response.getHeader(headerName));
        });
        headers.put("content-type", response.getContentType());
        headers.put("content-length", response.getContentSize());
        headers.put("Locale", response.getLocale());
        headers.put("Encoding", response.getCharacterEncoding());

        formatted.put("headers", headers);

        // Get & set request body content
        byte[] contentAsByteArray = response.getContentAsByteArray();
        String contentString = getContentAsString(contentAsByteArray, maxContentLength, charset);
        try {
            // Content string 을 json map object 로 변환
            Map<String, Object> content = objectMapper.readValue(contentString, new TypeReference<Map<String, Object>>() {});
            formatted.put("body", content);
        } catch(Exception e) {
            formatted.put("body", contentString);
        }

        return formatted;
    }

    /**
     * Byte array 를 문자열로 바꿔서 반환하는 메소드
     * @param buf 변환할 byte array
     * @param maxLength 최대 길이
     * @param charset UTF-8 등 인코딩 방법
     * @return byte array 를 문자열로 바꾼 결과
     */
    private String getContentAsString(byte[] buf, int maxLength, String charset) {
        if(buf == null || buf.length == 0) return "";
        String contentString = null;
        int length = Math.min(buf.length, maxLength);
        try {
            // Convert byte array to string
            contentString = new String(buf, 0, length, charset);
        } catch (UnsupportedEncodingException e) {
            contentString =  "Unsupported encoding: " + charset;
        }

        // Remove all tab and new-line
        contentString = contentString.replaceAll("[\\n\\t]", "");

        return contentString;
    }
}
