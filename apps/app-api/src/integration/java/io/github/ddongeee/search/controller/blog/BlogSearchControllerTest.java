package io.github.ddongeee.search.controller.blog;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.generator.BuilderArbitraryGenerator;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.github.ddongeee.search.BlogApiTest;
import io.github.ddongeee.search.port.input.Pagination;
import io.github.ddongeee.search.port.input.blog.SearchBlogUseCase;
import io.github.ddongeee.search.port.input.blog.response.BlogDocumentDto;
import io.github.ddongeee.search.port.input.blog.response.BlogDto;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@ExtendWith(MockitoExtension.class)
public class BlogSearchControllerTest extends BlogApiTest {
    @Mock
    private SearchBlogUseCase searchBlogUseCase;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        var blogSearchController = new BlogSearchController(searchBlogUseCase);

        standaloneSetup(
                MockMvcBuilders
                        .standaloneSetup(blogSearchController)
                        .apply(documentationConfiguration(restDocumentation))
        );
    }

    @Test
    void searchBlogs() {
        when(searchBlogUseCase.search(any()))
                .thenReturn(getMockBlogDto());

        var keyword = "SpringFramework";

        var response = given()
                .accept(ContentType.JSON)
                .param("keyword", keyword)
//                .param("url", "http://tistory.com")
                .param("sort", "ACCURACY")
                .param("page", 1)
                .param("size", 10)
                .when()
                .get("/api/v1/search/blog");
        response.prettyPrint();

        response.then()
                .status(HttpStatus.OK)
                .apply(
                        document(
                                "search",
                                resourceDetails().tag("블로그").description("블로그 조회 (페이징)"),
                                defaultPreprocessRequest(),
                                defaultPreprocessResponse(),
                                requestParameters(
                                        parameterWithName("keyword").description("블로그 검색 키워드"),
                                        parameterWithName("url").description("블로그 검색 주소").optional(),
                                        parameterWithName("sort").description("정렬 기준 [정확도순: ACCURACY, 최신순: RECENCY]").optional(),
                                        parameterWithName("page").description("전시 유무").optional(),
                                        parameterWithName("size").description("페이지 번호").optional()
                                ),
                                responseFields(
                                        fieldWithPath("success")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("성공 여부"),
                                        fieldWithPath("code")
                                                .type(JsonFieldType.STRING)
                                                .description("code"),
                                        fieldWithPath("message")
                                                .type(JsonFieldType.STRING)
                                                .description("message")
                                                .optional(),
                                        fieldWithPath("data")
                                                .type(JsonFieldType.OBJECT)
                                                .description("데이터"),
                                        fieldWithPath("data.documents[]")
                                                .type(JsonFieldType.ARRAY)
                                                .description("블로그 문서 목록"),
                                        fieldWithPath("data.documents[].title")
                                                .type(JsonFieldType.STRING)
                                                .description("블로그 제목"),
                                        fieldWithPath("data.documents[].contents")
                                                .type(JsonFieldType.STRING)
                                                .description("블로그 내용"),
                                        fieldWithPath("data.documents[].url")
                                                .type(JsonFieldType.STRING)
                                                .description("블로그 주소"),
                                        fieldWithPath("data.documents[].blogName")
                                                .type(JsonFieldType.STRING)
                                                .description("블로그 이름"),
                                        fieldWithPath("data.documents[].thumbnail")
                                                .type(JsonFieldType.STRING)
                                                .description("이미지 썸네일"),
                                        fieldWithPath("data.documents[].writtenAt")
                                                .type(JsonFieldType.STRING)
                                                .description("블로그 글 작성 시간"),
                                        subsectionWithPath("data.pagination")
                                                .type(JsonFieldType.OBJECT)
                                                .description("페이지네이션")
                                )
                        )
                );
    }

    private BlogDto getMockBlogDto() {
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(BlogDocumentDto.class, BuilderArbitraryGenerator.INSTANCE)
                .build();

        var sampleBlogs = fixtureMonkey.giveMeBuilder(BlogDocumentDto.class)
                .setNotNull("title")
                .setNotNull("contents")
                .setNotNull("url")
                .setNotNull("blogName")
                .setNotNull("thumbnail")
                .setNotNull("writtenAt")
                .sampleList(10);

        return BlogDto.builder()
                .documents(sampleBlogs)
                .pagination(Pagination.empty())
                .build();
    }
}
