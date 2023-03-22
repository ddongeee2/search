package io.github.ddongeee.search.converter.statistics;

import io.github.ddongeee.search.configure.MapStructConfig;
import org.mapstruct.Mapper;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;
import io.github.ddongeee.search.port.input.statistics.response.BlogStatisticsDto;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface BlogStatisticsDtoConverter {
    BlogStatisticsDto convert(BlogStatistic source);
    List<BlogStatisticsDto> convert(List<BlogStatistic> sources);
}
