package io.github.ddongeee.search.http.blog.kakao;

import java.beans.ConstructorProperties;
import java.time.ZonedDateTime;
import java.util.List;

record KakaoBlogResponse(KakaoBlogMeta meta,
                         List<KakaoBlogDocument> documents
) {
    @ConstructorProperties({"meta", "documents"})
    public KakaoBlogResponse {}

    record KakaoBlogDocument(String title,
                             String contents,
                             String url,
                             String blogname,
                             String thumbnail,
                             ZonedDateTime datetime
    ) {
        @ConstructorProperties({"title", "contents", "url", "blogname", "thumbnail", "datetime"})
        public KakaoBlogDocument {}
    }
}
