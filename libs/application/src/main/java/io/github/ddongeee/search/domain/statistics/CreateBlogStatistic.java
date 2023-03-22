package io.github.ddongeee.search.domain.statistics;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateBlogStatistic {
    @NotBlank
    private final String keyword;

    @Builder
    public CreateBlogStatistic(String keyword) {
        this.keyword = keyword;
    }
}
