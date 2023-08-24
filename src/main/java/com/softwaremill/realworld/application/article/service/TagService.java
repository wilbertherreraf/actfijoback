package com.softwaremill.realworld.application.article.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softwaremill.realworld.domain.article.Tag;
import com.softwaremill.realworld.domain.article.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<String> getTags() {
        return tagRepository.findAll().stream().map(Tag::getName).toList();
    }
}
