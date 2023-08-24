package gob.gamo.activosf.app.domain.entities;

import java.util.Objects;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "sec_userrol")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Userrol {
    @EmbeddedId
    private UserrolId id;

    @MapsId("userId")
    @JoinColumn(name = "uro_usrid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("rolId")
    @JoinColumn(name = "uro_rolid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Roles rol;
    /*
        @CreatedDate
        @Builder.Default
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    */
    public Userrol(User user, Roles rol) {
        this.id = new UserrolId(user.getId(), rol.getId());
        this.user = user;
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Userrol other
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.user, other.user)
                && Objects.equals(this.rol, other.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.user, this.user);
    }
}
