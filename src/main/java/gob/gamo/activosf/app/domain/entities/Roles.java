package gob.gamo.activosf.app.domain.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
@Table(name = "sec_roles")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Roles {
    @Id
    @Column(name = "rol_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rol_codrol")
    private String codrol;

    @Column(name = "rol_descrip")
    private String descripcion;

    @JoinColumns({
            @JoinColumn(name = "rol_tabstatreg", referencedColumnName = "des_codtab"),
            @JoinColumn(name = "rol_statreg", referencedColumnName = "des_codigo") })
    @ManyToOne(fetch = FetchType.LAZY)
    private GenDesctabla estado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Profile> includeRecursos = new HashSet<>();

    @Builder
    private Roles(Integer id, String codrol, String descripcion){
        this.id = id;
        this.codrol = codrol;
        this.descripcion = descripcion;
    }

    public void updateDescripcion(String desc){
        this.descripcion = desc;
    }
    public void addRecurso( Recurso recurso) {
        Profile profile = new Profile(this, recurso);

        if (this.includeRecursos.stream().anyMatch(profile::equals)) {
            return;
        }

        this.includeRecursos.add(profile);
    }

    public List<Recurso> getRecursos() {
        return this.includeRecursos.stream().map(Profile::getRecurso).toList();
    }

    public String[] getCodrecursos() {
        return this.getRecursos().stream().map(Recurso::getCodrec).sorted().toArray(String[]::new);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Roles other && Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }    
    
}
