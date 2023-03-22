package io.github.ddongeee.search.contract.blog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BlogSearchQuerySort {
    ACCURACY("정확도순"),
    RECENCY("최신순");

    @Getter
    private final String name;
}
