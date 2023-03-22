package io.github.ddongeee.search.service.blog

import io.github.ddongeee.search.converter.blog.BlogDtoConverter
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase
import io.github.ddongeee.search.port.output.blog.BlogPort
import spock.lang.Specification
import static io.github.ddongeee.search.port.input.blog.SearchBlogUseCase.*

class BlogSearchServiceTest extends Specification {
    var blogPort = Mock(BlogPort)
    var upsertBlogStatisticsUseCase = Mock(UpsertBlogStatisticsUseCase)
    var blogDtoConverter = Mock(BlogDtoConverter)
    var sut = new BlogSearchService(blogPort , upsertBlogStatisticsUseCase, blogDtoConverter)

    def "블로그 검색시에 검색건수업데이트, 블로그검색, 컨버터가 정상적으로 호출된다."() {
        given:
        var command = GroovyMock(SearchCommand.class)

        when:
        sut.search(command);

        then:
        1 * upsertBlogStatisticsUseCase.increaseCountOrCreate(_)
        1 * blogPort.searchBlogDocuments(_)
        1 * blogDtoConverter.convert(_)
    }
}
