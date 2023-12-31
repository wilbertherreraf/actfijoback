package com.softwaremill.realworld.domain.article;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.softwaremill.realworld.domain.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(length = 50, unique = true, nullable = false)
    private String title;

    @Column(length = 50, unique = true, nullable = false)
    private String slug;

    @Column(length = 1_000, nullable = false)
    private String content = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleFavorite> favoriteUsers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleTag> includeTags = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private Article(Integer id, User author, String description, String title, String content) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.title = title;
        this.slug = createSlugBy(title);
        this.content = content;
        this.favoriteUsers = new HashSet<>();
        this.includeTags = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    public void updateTitle(String title) {
        if (title.isBlank()) {
            log.info("Title is blank.");
            return;
        }

        this.title = title;
        this.slug = createSlugBy(title);
    }

    public void updateDescription(String description) {
        if (description.isBlank()) {
            log.info("Description is blank.");
            return;
        }

        this.description = description;
    }

    public void updateContent(String content) {
        if (content.isBlank()) {
            log.info("Content is blank.");
            return;
        }

        this.content = content;
    }

    public boolean isNotWritten(User user) {
        return !this.author.equals(user);
    }

    public Integer numberOfLikes() {
        return this.favoriteUsers.size();
    }

    public void addTag(Tag tag) {
        ArticleTag articleTag = new ArticleTag(this, tag);

        if (this.includeTags.stream().anyMatch(articleTag::equals)) {
            return;
        }

        this.includeTags.add(articleTag);
    }

    public List<Tag> getTags() {
        return this.includeTags.stream().map(ArticleTag::getTag).toList();
    }

    public String[] getTagNames() {
        return this.getTags().stream().map(Tag::getName).sorted().toArray(String[]::new);
    }

    public boolean equalsArticle(ArticleFavorite articleFavorite) {
        return Objects.equals(this, articleFavorite.getArticle());
    }

    private String createSlugBy(String title) {
        return title.toLowerCase().replaceAll("\\s+", "-");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Article other && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
