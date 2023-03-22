package io.github.ddongeee.search.service.statistics

import io.github.ddongeee.search.domain.statistics.BlogStatistic
import io.github.ddongeee.search.port.output.statistics.BlogStatisticsPort
import org.springframework.transaction.support.TransactionOperations
import spock.lang.Specification
import static io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase.UpsertBlogStatisticsCommand

class BlogStatisticsUpsertServiceTest extends Specification {
    var blogStatisticsPort = Mock(BlogStatisticsPort)
    var sut = new BlogStatisticsUpsertService(blogStatisticsPort, TransactionOperations.withoutTransaction())


    def "검색어가 조회시, 조회수 1 증가."() {
        given:
        var command = UpsertBlogStatisticsCommand.of("keyword")

        var blogStat = BlogStatistic.builder()
                .count(11L)
                .build()
        blogStatisticsPort.findByKeyword(command.keyword()) >> Optional.of(blogStat)

        when:
        sut.increaseCountOrCreate(command)

        then:
        blogStat.getCount() == 12L
    }

    def "최초 조회되는 검색어이면, 새로운 엔티티를 생성"() {
        given:
        var command = UpsertBlogStatisticsCommand.of("keyword")
        blogStatisticsPort.findByKeyword(command.keyword()) >> Optional.empty()

        when:
        sut.increaseCountOrCreate(command)

        then:
        1 * blogStatisticsPort.save(_)
    }
}
