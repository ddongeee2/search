package io.github.ddongeee.search;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class})
public abstract class BlogApiTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssuredMockMvc.webAppContextSetup(
                webApplicationContext,
                documentationConfiguration(restDocumentation)
        );
    }

    protected OperationRequestPreprocessor defaultPreprocessRequest() {
        return preprocessRequest(
                prettyPrint()
        );
    }

    protected OperationResponsePreprocessor defaultPreprocessResponse() {
        return preprocessResponse(
                prettyPrint()
        );
    }
}
