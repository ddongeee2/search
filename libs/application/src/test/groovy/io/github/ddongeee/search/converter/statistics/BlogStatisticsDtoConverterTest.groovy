package io.github.ddongeee.search.converter.statistics

import io.github.ddongeee.search.domain.statistics.BlogStatistic
import spock.lang.Specification

class BlogStatisticsDtoConverterTest extends Specification {
    def sut = new BlogStatisticsDtoConverterImpl()

    def "BlogStatistic 을 BlogStatisticDto 로 변환"() {
        given:
        var blogStat = Mock(BlogStatistic) {
            getCount() >> 9
            getKeyword() >> "testKeyword"
        }
        when:
        var blogStatDto = sut.convert(blogStat);
        then:
        blogStatDto != null
        blogStatDto.getCount()   == blogStat.getCount()
        blogStatDto.getKeyword() == blogStat.getKeyword()
    }
}
