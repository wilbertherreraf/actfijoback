package com.softwaremill.realworld.domain.article;

import com.softwaremill.realworld.domain.user.ProfileVO;
import com.softwaremill.realworld.domain.user.User;

import java.time.LocalDateTime;

public record ArticleVO(
        String slug,
        String title,
        String description,
        String body,
        String[] tagList,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean favorited,
        Integer favoritesCount,
        ProfileVO author) {
    public ArticleVO(User me, Article article) {
        this(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getContent(),
                article.getTagNames(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                me != null && me.isAlreadyFavorite(article),
                article.numberOfLikes(),
                new ProfileVO(me, article.getAuthor()));
    }
}
