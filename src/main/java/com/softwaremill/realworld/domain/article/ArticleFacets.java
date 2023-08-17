package com.softwaremill.realworld.domain.article;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record ArticleFacets(String tag, String author, String favorited, Integer offset, Integer limit) {
    public ArticleFacets {
        if (offset < 0) {
            offset = 0;
        }

        if (limit < 0 || limit > 100) {
            limit = 20;
        }
    }

    public Pageable getPageable() {
        return PageRequest.of(offset, limit);
    }
}
