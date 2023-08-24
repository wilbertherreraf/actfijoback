package com.softwaremill.realworld.application.article.controller;

import java.util.List;

import com.softwaremill.realworld.domain.article.ArticleVO;

public record MultipleArticlesResponse(ArticleVO[] articles, Integer articlesCount) {
    public MultipleArticlesResponse(List<ArticleVO> articles) {
        this(articles.toArray(ArticleVO[]::new), articles.size());
    }
}
