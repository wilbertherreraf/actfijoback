package gob.gamo.activosf.app.domain.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Entity
@Table(name = "sec_roles")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
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
        @JoinColumn(name = "rol_statreg", referencedColumnName = "des_codigo")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private GenDesctabla estado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Profile> includeRecursos = new HashSet<>();

    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sec_profile",
            joinColumns = @JoinColumn(name = "prr_rolid"),
            inverseJoinColumns = @JoinColumn(name = "prr_resid"))
    private Set<Recurso> recursos = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<User> usuarios = new HashSet<>();

    @Builder
    private Roles(Integer id, String codrol, String descripcion) {
        this.id = id;
        this.codrol = codrol;
        this.descripcion = descripcion;
    }

    public void updateDescripcion(String desc) {
        this.descripcion = desc;
    }

    public void addRecurso(Recurso recurso) {
        Profile profile = new Profile(this, recurso);

        if (this.includeRecursos.stream().anyMatch(profile::equals)) {
            return;
        }

        this.includeRecursos.add(profile);
        String s = this.getIncludeRecursos().stream().map(x -> " :: "+x.getId().getRolId()+"-" + x.getId().getRecursoId()).collect(Collectors.toList()).toString();
        log.info("XXXXXXX {}", s);
    }

    /*     public List<Recurso> getRecursos() {
        return this.includeRecursos.stream().map(Profile::getRecurso).toList();
    } */

    public Set<String> getCodrecursos() {
        // return this.getRecursos().stream().map(Recurso::getCodrec).sorted().toArray(String[]::new);
        return this.recursos.stream().map(Recurso::getCodrec).sorted().collect(Collectors.toSet());
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
