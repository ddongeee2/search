package io.github.ddongeee.search.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.ddongeee.search.converter.blog.BlogDtoConverter;
import io.github.ddongeee.search.port.input.blog.SearchBlogUseCase;
import io.github.ddongeee.search.port.input.blog.response.BlogDto;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.UpsertBlogStatisticsUseCase.UpsertBlogStatisticsCommand;
import io.github.ddongeee.search.port.output.blog.BlogPort;
import io.github.ddongeee.search.port.output.blog.BlogPort.SearchClause;

@Service
@RequiredArgsConstructor
public class BlogSearchService implements SearchBlogUseCase {
    private final BlogPort blogPort;
    private final UpsertBlogStatisticsUseCase upsertBlogStatisticsUseCase;
    private final BlogDtoConverter blogDtoConverter;

    @Override
    public BlogDto search(SearchCommand command) {
        var upsertCommand = UpsertBlogStatisticsCommand.of(command.keyword());
        upsertBlogStatisticsUseCase.increaseCountOrCreate(upsertCommand);

        final var clause = SearchClause.builder()
                .keyword(command.keyword())
                .restApiType(command.restApiType())
                .sort(command.sort())
                .page(command.page())
                .size(command.size())
                .build();

        final var blog = blogPort.searchBlogDocuments(clause);

        // 객체 변환
        return blogDtoConverter.convert(blog);
    }
}
