package io.github.ddongeee.search.port.input.blog.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

public record BlogDocumentDto(
        String title,
        String contents,
        String url,
        String blogName,
        String thumbnail,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        ZonedDateTime writtenAt
) {
    @Builder
    public BlogDocumentDto {}
}
