package gob.gamo.activosf.app.domain.entities;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the gen_tablas database table.
 * 
 */
@Entity
@Table(name="gen_tablas")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenTablas {

	@Id
	@Column(name="tab_codigo")
	private Integer codigo;

	@Column(name="tab_descripcion")
	private String descripcion;

	
}