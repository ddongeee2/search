package io.github.ddongeee.search.http.blog.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class KakaoBlogMeta {
    @JsonProperty("total_count")
    private final Long totalCount;
    @JsonProperty("pageable_count")
    private final int pageableCount;
    @JsonProperty("is_end")
    private final boolean isEnd;

    @ConstructorProperties({"totalCount", "pageableCount", "isEnd"})
    public KakaoBlogMeta(final Long totalCount, final int pageableCount, final boolean isEnd) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.isEnd = isEnd;
    }
}
