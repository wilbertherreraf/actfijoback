package gob.gamo.activosf.app.domain.entities;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Builder
@Table(name = "sec_usuario")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usr_login")
    private String username;

    // @Column(name = "usr_codemp")
    @Transient
    private String codEmpleado;

    @Column(name = "usr_nombres")
    private String nombres;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_password")
    private String password;

    @Column(name = "id_unid_empl")
    private Integer idUnidEmpl;

    @JoinColumns({
        @JoinColumn(name = "usr_tabtipousr", referencedColumnName = "des_codtab"),
        @JoinColumn(name = "usr_tipousr", referencedColumnName = "des_codigo")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private GenDesctabla tipoUsuario;

    @JoinColumns({
        @JoinColumn(name = "usr_tabstatuser", referencedColumnName = "des_codtab"),
        @JoinColumn(name = "usr_statuser", referencedColumnName = "des_codigo")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private GenDesctabla estado;

    @Transient
    private String token;

    @Transient
    @Builder.Default
    private boolean anonymous = false;

    public static User anonymous() {
        return User.builder().id(null).anonymous(true).build();
    }

    public User possessToken(String token) {
        this.token = token;
        return this;
    }
}
