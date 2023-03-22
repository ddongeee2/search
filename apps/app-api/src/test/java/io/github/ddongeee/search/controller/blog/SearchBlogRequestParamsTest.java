package io.github.ddongeee.search.controller.blog;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static io.github.ddongeee.search.controller.blog.BlogSearchController.*;
import static org.assertj.core.api.Assertions.assertThat;

class SearchBlogRequestParamsTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @DisplayName("블로그 검색을 하기 위해서는 keyword 는 필수이다.")
    @Test
    void keywordIsMandatory() {
        // given
        SearchBlogRequestParams request = new SearchBlogRequestParams(null, null, null, null, null);

        // when
        Set<ConstraintViolation<SearchBlogRequestParams>> violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("키워드를 입력하세요.");
        });
    }
}
