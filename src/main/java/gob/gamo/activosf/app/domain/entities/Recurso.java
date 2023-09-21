package gob.gamo.activosf.app.domain.entities;

import java.util.Objects;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
@Entity
@Table(name = "sec_recurso")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
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
    // @JsonIgnore
    /* @ManyToMany(mappedBy = "recursos")
    private Set<Roles> roles = new HashSet<>(); */

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
