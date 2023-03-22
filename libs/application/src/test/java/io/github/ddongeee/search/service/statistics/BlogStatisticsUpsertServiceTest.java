package io.github.ddongeee.search.service.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionOperations;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase.UpsertBlogStatisticsCommand;
import io.github.ddongeee.search.port.output.statistics.BlogStatisticsPort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlogStatisticsUpsertServiceTest {
    @InjectMocks
    BlogStatisticsUpsertService sut;

    @Mock
    BlogStatisticsPort blogStatisticsPort;

    @BeforeEach
    void setup() {
        sut = new BlogStatisticsUpsertService(
                blogStatisticsPort,
                TransactionOperations.withoutTransaction()
        );
    }

    @DisplayName("검색어가 조회되면 조회수를 하나 증가한다.")
    @Test
    void increaseCountWhenKeywordIsFound() {
        // given
        var command = UpsertBlogStatisticsCommand.builder()
                .keyword("keyword")
                .build();

        var blogStat = BlogStatistic.builder()
                .count(11L)
                .build();

        given(blogStatisticsPort.findByKeyword(command.keyword()))
                .willReturn(Optional.of(blogStat));

        // when
        sut.increaseCountOrCreate(command);

        // then
        assertThat(blogStat.getCount()).isEqualTo(12L);
    }

    @DisplayName("검색어가 조회 되지 않으면 새로운 엔티티를 생성한다.")
    @Test
    void newEntityWhenKeywordIsNotFound() {
        // given
        var command = UpsertBlogStatisticsCommand.builder()
                .keyword("keyword")
                .build();

        given(blogStatisticsPort.findByKeyword(command.keyword()))
                .willReturn(Optional.empty());

        // when
        sut.increaseCountOrCreate(command);

        // then
        verify(blogStatisticsPort, times(1))
                .save(any(BlogStatistic.class));
    }
}
