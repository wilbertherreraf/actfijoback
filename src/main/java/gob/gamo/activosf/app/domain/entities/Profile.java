package gob.gamo.activosf.app.domain.entities;

import java.util.Objects;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property="idProfile")
@Entity
@Getter
@Builder
@Table(name = "sec_profile")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @EmbeddedId
    private ProfileId id;

    @MapsId("recursoId")
    @JoinColumn(name = "prr_resid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Recurso recurso;

    @MapsId("rolId")
    @JoinColumn(name = "prr_rolid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Roles rol;
    /*
        @CreatedDate
        @Builder.Default
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    */
    public Profile(Roles rol, Recurso resource) {
        this.id = new ProfileId(rol.getId(), resource.getId());
        this.rol = rol;
        this.recurso = resource;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Profile other
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.recurso, other.recurso)
                && Objects.equals(this.rol, other.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.recurso, this.rol);
    }
}
