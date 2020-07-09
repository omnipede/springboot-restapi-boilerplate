package io.omnipede.springbootrestapiboilerplate.global.filter;

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

/**
 * 모든 request와 response 를 logging 하는 필터
 * @see "https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl"
 * @see "https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl"
 */
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final int maxContentLength = 1024;
    private static final String charset = "UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 시간 측정 시작
        long startTime = System.currentTimeMillis();

        // Get http method, url, query string
        StringBuffer reqInfo = new StringBuffer();
        reqInfo.append(request.getMethod())
                .append(" ")
                .append(request.getRequestURL());
        String queryString = request.getQueryString();
        if (queryString != null) {
            reqInfo.append("?").append(queryString);
        }

        logger.info("=> " + reqInfo);

        // Request body의 Inputstream을 읽으면 유실되는 문제를 해결하기 위해 다음과 같이 wrapping 한다.
        // @see "https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl"
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Continue
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // Response 까지 걸린 시간
        long duration = System.currentTimeMillis() - startTime;

        // Get request body
        String requestBody = getContentAsString(wrappedRequest.getContentAsByteArray(), maxContentLength, charset);
        if (requestBody.length() > 0)
            // Log request body
            logger.info("   Request body: " + requestBody);

        // Log response info
        logger.info("<= " + reqInfo + ": returned status = " + response.getStatus() + " in " + duration + "ms");

        // Get response body
        String responseBody = getContentAsString(wrappedResponse.getContentAsByteArray(), maxContentLength, charset);
        if (responseBody.length() > 0)
            // Log response body
            logger.info("   Response body: " + responseBody);

        // (!) IMPORTANT: copy content of response back into original response
        wrappedResponse.copyBodyToResponse();
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
