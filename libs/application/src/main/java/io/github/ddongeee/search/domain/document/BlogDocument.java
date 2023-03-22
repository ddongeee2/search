package io.github.ddongeee.search.domain.document;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

public record BlogDocument(
        String title,
        String contents,
        String url,
        String blogName,
        String thumbnail,
        ZonedDateTime writtenAt
) {
    @Builder
    public BlogDocument {}
}
