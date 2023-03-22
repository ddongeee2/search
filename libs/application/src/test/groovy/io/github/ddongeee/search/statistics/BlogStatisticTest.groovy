package io.github.ddongeee.search.statistics

import io.github.ddongeee.search.domain.statistics.BlogStatistic
import io.github.ddongeee.search.domain.statistics.CreateBlogStatistic
import spock.lang.Specification

class BlogStatisticTest extends Specification {
    def "블로그 통계 엔티티를 생성"() {
        given:
        var create = Mock(CreateBlogStatistic) { getKeyword() >> "keyword" }
        expect:
        var actual = BlogStatistic.create(create)
        actual.getKeyword() == create.getKeyword()
        actual.getCount() == 1L
    }

    def "조회수를 증가한다"() {
        given:
        var create = Mock(CreateBlogStatistic) { getKeyword() >> "keyword" }
        expect:
        var actual = BlogStatistic.create(create)
        actual.increaseCount()
        actual.getKeyword() == create.getKeyword()
        actual.getCount() == 2L
    }
}
