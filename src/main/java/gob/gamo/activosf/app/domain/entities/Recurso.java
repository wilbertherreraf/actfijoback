package gob.gamo.activosf.app.domain.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sec_recurso")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recurso {
    @Id
    @Column(name = "res_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "res_codrec")
    private String codrec;

    @Column(name = "res_descrip")
    private String descrip;

    /*     @Column(name = "res_url")
    private String url; */

    @ManyToMany(mappedBy = "recursos")
    private Set<Roles> roles = new HashSet<>();

    public Recurso(String codrec, String descripcion) {
        this.codrec = codrec;
        this.descrip = descripcion;
    }

    public void permissioning(Roles rol) {
        rol.addRecurso(this);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Recurso other
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.codrec, other.codrec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.codrec);
    }
}
