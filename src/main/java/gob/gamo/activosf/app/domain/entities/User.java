package gob.gamo.activosf.app.domain.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@AllArgsConstructor
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

    @JsonIgnore
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sec_userrol", //
            joinColumns = @JoinColumn(name = "uro_usrid") //
            ,
            inverseJoinColumns = @JoinColumn(name = "uro_rolid") //
            )
    private Set<Roles> roles = new HashSet<>();

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

    /*     public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateImage(String imageUrl) {
        this.image = imageUrl;
    } */
}
