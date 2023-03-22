package io.github.ddongeee.search.service.blog;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.generator.BuilderArbitraryGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.ddongeee.search.converter.blog.BlogDtoConverter;
import io.github.ddongeee.search.domain.document.Blog;
import io.github.ddongeee.search.domain.document.BlogDocument;
import io.github.ddongeee.search.port.input.Pagination;
import io.github.ddongeee.search.port.input.blog.SearchBlogUseCase.SearchCommand;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase.UpsertBlogStatisticsCommand;
import io.github.ddongeee.search.port.output.blog.BlogPort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceTest {
    @InjectMocks
    BlogSearchService sut;

    @Mock
    BlogPort blogPort;

    @Mock
    UpsertBlogStatisticsUseCase upsertBlogStatisticsUseCase;

    @Mock
    BlogDtoConverter blogDtoConverter;


    @BeforeEach
    void setup() {
        sut = new BlogSearchService(
                blogPort,
                upsertBlogStatisticsUseCase,
                blogDtoConverter
        );
    }

    @DisplayName("블로그를 조회할 땐, 검색어를 저장한다.")
    @Test
    void saveKeywordWhenSearchingBlogs() {
        // given
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(SearchCommand.class, BuilderArbitraryGenerator.INSTANCE)
                .build();
        var command = fixtureMonkey.giveMeOne(SearchCommand.class);

        // when
        sut.search(command);

        // then
        verify(upsertBlogStatisticsUseCase, times(1))
                .increaseCountOrCreate(any(UpsertBlogStatisticsCommand.class));
    }

    @DisplayName("키워드로 블로그를 조회할 수 있다.")
    @Test
    void ableToSearchBlogsByKeyword() {
        // given
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(SearchCommand.class, BuilderArbitraryGenerator.INSTANCE)
                .build();
        var command = fixtureMonkey.giveMeOne(SearchCommand.class);

        // when
        sut.search(command);

        // then
//        verify(blogPort, times(1))
//                .searchBlogDocuments(any(KakaoBlogSearchClause.class));
    }

    @DisplayName("조회한 결과를 컨버터를 사용하여 변환한다.")
    @Test
    void convertResponseByConverter() {
        // given
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(SearchCommand.class, BuilderArbitraryGenerator.INSTANCE)
                .putGenerator(Blog.class, BuilderArbitraryGenerator.INSTANCE)
                .putGenerator(Pagination.class, BuilderArbitraryGenerator.INSTANCE)
                .putGenerator(BlogDocument.class, BuilderArbitraryGenerator.INSTANCE)
                .build();
        var command = fixtureMonkey.giveMeOne(SearchCommand.class);
        var blog = fixtureMonkey.giveMeOne(Blog.class);

        given(blogPort.searchBlogDocuments(any())).willReturn(blog);

        // when
        sut.search(command);

        // then
        verify(blogDtoConverter, times(1))
                .convert(any(Blog.class));
    }
}
