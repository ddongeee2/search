package io.github.ddongeee.search.service.statistics;

import io.github.ddongeee.search.converter.statistics.BlogStatisticsDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;
import io.github.ddongeee.search.port.input.statistics.SearchBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.SearchBlogStatisticsUseCase.SearchBlogStatisticsCommand;
import io.github.ddongeee.search.port.input.statistics.response.BlogStatisticsDto;
import io.github.ddongeee.search.port.output.statistics.BlogStatisticsPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogStatisticsSearchService implements SearchBlogStatisticsUseCase {
    private final BlogStatisticsPort blogStatisticsPort;
    private final TransactionOperations readTransactionOperations;
    private final BlogStatisticsDtoConverter blogStatisticsDtoConverter;

    @Override
    public List<BlogStatisticsDto> search(final SearchBlogStatisticsCommand command) {
        var stats = readTransactionOperations.execute(status -> blogStatisticsPort.findTopNByOrderByCountDesc(command.top()));

        return blogStatisticsDtoConverter.convert(stats);
    }
}
