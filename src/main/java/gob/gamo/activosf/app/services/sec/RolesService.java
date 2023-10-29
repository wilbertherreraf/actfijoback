package gob.gamo.activosf.app.services.sec;

import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.sec.ProfileRepository;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.UserSpecification;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final RecursoRepository resourceRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
    public RolesVO getSingleRol(String codrol) {
        Roles article = findByCodrol(codrol);
        return new RolesVO(article);
    }

    @Transactional(readOnly = true)
    public Page<Roles> getRoles(String searchTxt, Pageable pageable) {
        CriteriaParser parser = new CriteriaParser();
        Deque<?> deque = parser.parse(searchTxt);
        if (deque.size() > 0) {
            GenericSpecificationsBuilder<Roles> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<Roles> spec = specBuilder.build(deque, UserSpecification::new);
            Page<Roles> list0 = rolesRepository.findAll(spec, pageable);
            return list0;
        }
        Page<Roles> list = rolesRepository.findAll(pageable);
        return list;
    }

    @Transactional
    public RolesVO createRol(User me, RolesVO request) {
        Roles nRol = Roles.builder()
                .codrol(request.codrol())
                .descripcion(request.descripcion())
                .build();
        Roles newRol = rolesRepository.save(nRol);

        for (String codRecurso : request.permisosList()) {
            Recurso recurso = resourceRepository
                    .findByCodrec(codRecurso)
                    .orElseThrow(() -> new DataException("Registro inexistente " + codRecurso));
            recurso.permissioning(newRol);
            profileRepository.save(new Profile(nRol, recurso));
        }

        newRol = rolesRepository
                .findByCodrol(newRol.getCodrol())
                .orElseThrow(() -> new DataException("registro no fue creado"));
        RolesVO rolesVO = new RolesVO(newRol);
        return rolesVO;
    }

    @Transactional
    public RolesVO updateRol(User me, String codrol, RolesVO request) {
        Roles rol = rolesRepository
                .findByCodrol(codrol)
                .orElseThrow(() -> new NoSuchElementException("Rol inexistente para codigo: `%s`".formatted(codrol)));

        profileService.deleteByRol(rol.getId());

        updateFromVO(rol, request);
        for (String codRecurso : request.permisosList()) {
            Recurso recurso = resourceRepository
                    .findByCodrec(codRecurso)
                    .orElseThrow(() -> new DataException("Registro inexistente " + codRecurso));
            Optional<Profile> profile = profileRepository.findByRolIdRecursoId(rol.getId(), recurso.getId());

            if (!profile.isPresent()) {
                recurso.permissioning(rol);
                profileRepository.save(new Profile(rol, recurso));
            }
        }

        Optional<Roles> rol0 = rolesRepository.findByCodrol(codrol);

        rol0.get().getIncludeRecursos().clear();

        rol0.get().getIncludeRecursos().addAll(profileRepository.findByIdRolId(rol.getId()));

        return new RolesVO(rol0.get());
    }

    @Transactional
    public void deleteRol(User me, String codrol) {
        Roles rol = rolesRepository
                .findByCodrol(codrol)
                .orElseThrow(() -> new NoSuchElementException("Rol inexistente para codigo: `%s`".formatted(codrol)));

        rolesRepository.delete(rol);
    }

    private Roles findByCodrol(String codrol) {
        return rolesRepository
                .findByCodrol(codrol)
                .orElseThrow(() -> new NoSuchElementException("Rol inexistente para codigo: `%s`".formatted(codrol)));
    }

    private void updateFromVO(Roles r, RolesVO rvo) {
        r.updateDescripcion(rvo.descripcion());
    }
}
