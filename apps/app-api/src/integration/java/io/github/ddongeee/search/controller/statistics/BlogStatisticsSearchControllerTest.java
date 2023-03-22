package io.github.ddongeee.search.controller.statistics;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.generator.BuilderArbitraryGenerator;
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
import io.github.ddongeee.search.port.input.statistics.SearchBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.response.BlogStatisticsDto;

import java.util.Comparator;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static net.jqwik.api.Arbitraries.longs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@ExtendWith(MockitoExtension.class)
public class BlogStatisticsSearchControllerTest extends BlogApiTest {
    @Mock
    private SearchBlogStatisticsUseCase searchBlogStatisticsUseCase;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        var blogStatisticsSearchController = new BlogStatisticsSearchController(searchBlogStatisticsUseCase);

        standaloneSetup(
                MockMvcBuilders
                        .standaloneSetup(blogStatisticsSearchController)
                        .apply(documentationConfiguration(restDocumentation))
        );
    }

    @Test
    void searchPopularSearchKeyword() {
        when(searchBlogStatisticsUseCase.search(any()))
                .thenReturn(getMockBlogStatistics());

        var response = given()
                .param("top", 10)
                .when()
                .get("/api/v1/blogs/statistics/popular");
        response.prettyPrint();

        response.then()
                .status(HttpStatus.OK)
                .apply(
                        document(
                                "search-blogs-statistics-popular",
                                resourceDetails().tag("블로그").description("인기 블로그 검색어 목록 조회 (상위 10개)"),
                                defaultPreprocessRequest(),
                                defaultPreprocessResponse(),
                                requestParameters(
                                        parameterWithName("top").description("인기 블로그 검색어 상위 n 개 (기본: 10개)").optional()
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
                                        fieldWithPath("data[]")
                                                .type(JsonFieldType.ARRAY)
                                                .description("인기 블로그 검색어 목록"),
                                        fieldWithPath("data[].keyword")
                                                .type(JsonFieldType.STRING)
                                                .description("인기 블로그 검색어"),
                                        fieldWithPath("data[].count")
                                                .type(JsonFieldType.NUMBER)
                                                .description("검색어 별 검색된 횟수")
                                )
                        )
                );
    }

    private List<BlogStatisticsDto> getMockBlogStatistics() {
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(BlogStatisticsDto.class, BuilderArbitraryGenerator.INSTANCE)
                .build();

        var sampleBlogs = fixtureMonkey.giveMeBuilder(BlogStatisticsDto.class)
                .setNotNull("keyword")
                .set("count", longs().greaterOrEqual(0))
                .sampleList(10);

        return sampleBlogs.stream().sorted(
                Comparator.comparing(BlogStatisticsDto::getCount).reversed()
        ).toList();
    }
}
