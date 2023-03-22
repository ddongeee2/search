package io.github.ddongeee.search.port.input;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

@Getter
public class Pagination {
    private final Integer currentPage;
    private final Integer totalPage;
    private final Long totalItemCount;
    private final Integer countPerPage;

    private boolean hasNextPage;
    private Integer nextPage;

    @Builder
    public Pagination(Integer currentPage,
                      Integer totalPage,
                      Long totalItemCount,
                      Integer countPerPage) {

        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalItemCount = totalItemCount;
        this.countPerPage = countPerPage;

        if (ObjectUtils.allNotNull(currentPage, totalPage)) {
            this.hasNextPage = currentPage < totalPage;
            this.nextPage = currentPage + 1;
        }
    }

    public static Pagination empty() {
        return Pagination.builder()
                .build();
    }
}
