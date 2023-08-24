package com.softwaremill.realworld.domain.user;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.softwaremill.realworld.domain.article.Article;
import com.softwaremill.realworld.domain.article.ArticleFavorite;
import com.softwaremill.realworld.domain.article.ArticleVO;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Builder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Builder.Default
    @Column(length = 500, nullable = false)
    private String bio = "";

    private String image;

    @CreatedDate
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> following = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "to", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> follower = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleFavorite> favoriteArticles = new HashSet<>();

    @Transient
    private String token;

    @Transient
    @Builder.Default
    private boolean anonymous = false;

    public static User anonymous() {
        return User.builder().id(null).anonymous(true).build();
    }

    public boolean isAnonymous() {
        return this.id == null && this.anonymous;
    }

    public boolean isAlreadyFollowing(User target) {
        Follow follow = new Follow(this, target);
        return this.following.stream().anyMatch(follow::equals);
    }

    public boolean isAlreadyFavorite(Article article) {
        ArticleFavorite articleFavorite = new ArticleFavorite(this, article);
        return this.favoriteArticles.stream().anyMatch(articleFavorite::equals);
    }

    public ProfileVO follow(User target) {
        if (isAlreadyFollowing(target)) {
            return new ProfileVO(this, target);
        }

        Follow follow = new Follow(this, target);
        addFollowingToCurrentUser(follow);
        addFollowerToTargetUser(follow);

        return new ProfileVO(this, target);
    }

    public ProfileVO unfollow(User target) {
        findFollowing(target).ifPresent(follow -> {
            this.removeFollowing(follow);
            target.removeFollower(follow);
        });

        return new ProfileVO(this, target);
    }

    public ArticleVO favorite(Article article) {
        if (isAlreadyFavorite(article)) {
            return new ArticleVO(this, article);
        }

        ArticleFavorite articleFavorite = new ArticleFavorite(this, article);
        addFavoriteArticle(articleFavorite);
        addThisUserToFavorite(articleFavorite);

        return new ArticleVO(this, article);
    }

    public ArticleVO unfavorite(Article article) {
        findArticleFavorite(article).ifPresent(articleFavorite -> {
            removeFavoriteArticle(articleFavorite);
            removeUserFromFavorite(articleFavorite);
        });

        return new ArticleVO(this, article);
    }

    public List<User> followUsers() {
        return this.following.stream().map(Follow::getTo).toList();
    }

    public User possessToken(String token) {
        this.token = token;
        return this;
    }

    public void updateEmail(String email) {
        if (email.isBlank() || this.email.equals(email)) {
            log.info("Email(`{}`) is blank or same as current email.", email);
            return;
        }

        // Note: Add email validation (ex. regex)
        this.email = email;
    }

    public void updateUsername(String username) {
        if (username.isBlank() || this.username.equals(username)) {
            log.info("Username(`{}`) is blank or same as current username.", username);
            return;
        }

        this.username = username;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String plaintext) {
        if (plaintext.isBlank()) {
            log.info("Password is blank.");
            return;
        }

        this.password = passwordEncoder.encode(plaintext);
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateImage(String imageUrl) {
        this.image = imageUrl;
    }

    private void addFollowingToCurrentUser(Follow follow) {
        this.following.add(follow);
    }

    private void addFollowerToTargetUser(Follow follow) {
        follow.getTo().getFollower().add(follow);
    }

    private Optional<Follow> findFollowing(User target) {
        return this.following.stream().filter(target::isFollowing).findFirst();
    }

    private boolean isFollowing(Follow follow) {
        return follow.getTo().equals(this);
    }

    private void removeFollowing(Follow follow) {
        this.following.remove(follow);
    }

    private void removeFollower(Follow follow) {
        this.follower.remove(follow);
    }

    private void addFavoriteArticle(ArticleFavorite articleFavorite) {
        this.favoriteArticles.add(articleFavorite);
    }

    private void addThisUserToFavorite(ArticleFavorite articleFavorite) {
        articleFavorite.getArticle().getFavoriteUsers().add(articleFavorite);
    }

    private Optional<ArticleFavorite> findArticleFavorite(Article article) {
        return this.favoriteArticles.stream().filter(article::equalsArticle).findFirst();
    }

    private void removeFavoriteArticle(ArticleFavorite articleFavorite) {
        this.favoriteArticles.remove(articleFavorite);
    }

    private void removeUserFromFavorite(ArticleFavorite articleFavorite) {
        articleFavorite.getArticle().getFavoriteUsers().remove(articleFavorite);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof User other && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
