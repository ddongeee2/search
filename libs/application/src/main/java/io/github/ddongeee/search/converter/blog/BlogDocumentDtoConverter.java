package io.github.ddongeee.search.converter.blog;

import io.github.ddongeee.search.configure.MapStructConfig;
import org.mapstruct.Mapper;
import io.github.ddongeee.search.domain.document.BlogDocument;
import io.github.ddongeee.search.port.input.blog.response.BlogDocumentDto;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface BlogDocumentDtoConverter {
    BlogDocumentDto convert(BlogDocument source);
    List<BlogDocumentDto> convert(List<BlogDocument> sources);
}
