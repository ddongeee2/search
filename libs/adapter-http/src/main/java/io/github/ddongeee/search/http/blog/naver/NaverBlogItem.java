package io.github.ddongeee.search.http.blog.naver;

import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class NaverBlogItem {
    private final String title;
    private final String link;
    private final String description;
    private final String bloggername;
    private final String bloggerlink;
    private final String postdate;

    @ConstructorProperties({
            "title", "link", "description",
            "bloggername", "bloggerlink", "postdate"
    })
    public NaverBlogItem(final String title, String link, String description,
                         final String bloggername, String bloggerlink,
                         final String postdate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.bloggername = bloggername;
        this.bloggerlink = bloggerlink;
        this.postdate = postdate;
    }
}
