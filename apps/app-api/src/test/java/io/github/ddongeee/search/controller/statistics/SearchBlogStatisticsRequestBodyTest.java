package io.github.ddongeee.search.controller.statistics;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static io.github.ddongeee.search.controller.statistics.BlogStatisticsSearchController.*;
import static net.jqwik.api.Arbitraries.longs;
import static org.assertj.core.api.Assertions.assertThat;

class SearchBlogStatisticsRequestBodyTest {

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

    @DisplayName("인기 검색어는 최소 1개에서 최대 10개까지 조회할 수 있다. (정상 케이스)")
    @Test
    void popularKeywordIsSearchableBetweenOneToTen() {
        // given
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .build();

        var inputs = fixtureMonkey.giveMeBuilder(Long.class)
                .set(longs().greaterOrEqual(1).lessOrEqual(10))
                .sampleList(100);

        inputs.forEach(input -> {
            var request = new SearchBlogStatisticsRequestBody(input);

            // when
            Set<ConstraintViolation<SearchBlogStatisticsRequestBody>> violations = validator.validate(request);

            // then
            assertThat(violations).isEmpty();
        });
    }

    @DisplayName("인기 검색어는 최소 1개에서 최대 10개까지 조회할 수 있다. (에러 케이스)")
    @Test
    void popularKeywordIsSearchableBetweenOneToTen_errorCase() {
        // given
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .build();

        var lessThanOnes = fixtureMonkey.giveMeBuilder(Long.class)
                .set(longs().lessOrEqual(0))
                .sampleList(100);

        var greaterThanTens = fixtureMonkey.giveMeBuilder(Long.class)
                .set(longs().greaterOrEqual(11))
                .sampleList(100);

        lessThanOnes.forEach(input -> {
            var request = new SearchBlogStatisticsRequestBody(input);

            // when
            Set<ConstraintViolation<SearchBlogStatisticsRequestBody>> violations = validator.validate(request);

            // then
            assertThat(violations).isNotEmpty();
        });

        greaterThanTens.forEach(input -> {
            var request = new SearchBlogStatisticsRequestBody(input);

            // when
            Set<ConstraintViolation<SearchBlogStatisticsRequestBody>> violations = validator.validate(request);

            // then
            assertThat(violations).isNotEmpty();
        });
    }

    @DisplayName("top 이 null 이면 기본값으로 호출한다.")
    @Test
    void callWithDefaultWhenTopIsNull() {
        // given
        var request = new SearchBlogStatisticsRequestBody(null);

        // when
        Set<ConstraintViolation<SearchBlogStatisticsRequestBody>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
        assertThat(request.getTop()).isEqualTo(10L);
    }
}
