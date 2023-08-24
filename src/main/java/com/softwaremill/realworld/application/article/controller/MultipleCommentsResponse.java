package com.softwaremill.realworld.application.article.controller;

import java.util.List;

import com.softwaremill.realworld.domain.article.CommentVO;

public record MultipleCommentsResponse(CommentVO[] comments) {
    public MultipleCommentsResponse(List<CommentVO> comments) {
        this(comments.toArray(CommentVO[]::new));
    }
}
