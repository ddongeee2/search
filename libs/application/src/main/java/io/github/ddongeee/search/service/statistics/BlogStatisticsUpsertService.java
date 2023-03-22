package io.github.ddongeee.search.service.statistics;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;
import io.github.ddongeee.search.domain.statistics.CreateBlogStatistic;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase.UpsertBlogStatisticsCommand;
import io.github.ddongeee.search.port.output.statistics.BlogStatisticsPort;

@Service
public class BlogStatisticsUpsertService implements UpsertBlogStatisticsUseCase {

    private final BlogStatisticsPort blogStatisticsPort;
    private final TransactionOperations writeTransactionOperations;

    public BlogStatisticsUpsertService(BlogStatisticsPort blogStatisticsPort,
                                       TransactionOperations writeTransactionOperations) {
        this.blogStatisticsPort = blogStatisticsPort;
        this.writeTransactionOperations = writeTransactionOperations;
    }

    @Override
    public void increaseCountOrCreate(UpsertBlogStatisticsCommand command) {
        writeTransactionOperations.executeWithoutResult(status -> {
            var statOptional = blogStatisticsPort.findByKeyword(
                    command.keyword()
            );

            statOptional.ifPresentOrElse(
                    BlogStatistic::increaseCount,
                    () -> createNewStat(command)
            );
        });
    }

    private void createNewStat(UpsertBlogStatisticsCommand command) {
        blogStatisticsPort.save(
                BlogStatistic.create(
                        CreateBlogStatistic.builder()
                                .keyword(command.keyword())
                                .build()
                ));
    }
}
