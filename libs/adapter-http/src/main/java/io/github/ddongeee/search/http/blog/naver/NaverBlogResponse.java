package io.github.ddongeee.search.http.blog.naver;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

@Getter
public class NaverBlogResponse {
    private final String lastBuildDate;
    private final Long total;
    private final Long start;
    private final Long display;
    private final List<NaverBlogItem> items;

    @ConstructorProperties({
            "lastBuildDate", "total", "start", "display", "items"
    })
    public NaverBlogResponse(final String lastBuildDate, Long total,
                             final Long start, Long display,
                             final List<NaverBlogItem> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }
}
