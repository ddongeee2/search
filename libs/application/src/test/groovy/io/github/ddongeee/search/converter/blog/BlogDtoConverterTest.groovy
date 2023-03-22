package io.github.ddongeee.search.converter.blog

import io.github.ddongeee.search.domain.document.Blog
import io.github.ddongeee.search.domain.document.BlogDocument
import spock.lang.Specification

class BlogDtoConverterTest extends Specification {
    def blogDocumentDtoConverter = Mock(BlogDocumentDtoConverter)
    def sut = new BlogDtoConverter(blogDocumentDtoConverter)

    def "Blog 를 BlogDto 로 변환"() {
        given:
        var dummyDoc = BlogDocument.builder()
                                                .title("testTitle")
                                                .contents("testContents")
                                                .url("testUrl")
                                                .blogName("testBlogName")
                                                .thumbnail("testThumbnail")
                                                .writtenAt(null)
                .build()
        var blog = Mock(Blog) {
            getBlogDocuments() >> [dummyDoc]
        }
        var firstBlog = blog.getBlogDocuments().get(0)

        blogDocumentDtoConverter.convert(_) >> [dummyDoc]

        when:
        var blogDto = sut.convert(blog)

        then:
        blogDto != null
        blogDto.documents().size()  == blog.getBlogDocuments().size()
        var doc = blogDto.documents().get(0)
        doc.title()     == firstBlog.title()
        doc.contents()  == firstBlog.contents()
        doc.url()       == firstBlog.url()
        doc.blogName()  == firstBlog.blogName()
        doc.thumbnail() == firstBlog.thumbnail()
        doc.writtenAt() == firstBlog.writtenAt()
    }
}
