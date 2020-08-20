package io.omnipede.springbootrestapiboilerplate.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtils {

    /**
     * Rest docs 의 json request 를 보기 좋게 formatting 해주는 preprocessor 를 반환하는 메소드
     * @return preprocessor
     */
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(prettyPrint());
    }

    /**
     * Rest docs의 json response 를 보기 좋게 formatting 해주는 preprocessor 를 반환하는 메소드
     * @return preprocessor
     */
    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
