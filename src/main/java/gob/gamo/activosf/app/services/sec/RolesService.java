package gob.gamo.activosf.app.services.sec;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.ProfileId;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.sec.ProfileRepository;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;

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
    public Page<Roles> getRoles(Pageable pageable) {
        Page<Roles> list = rolesRepository.findAll(pageable);
        return list;
    }

    @Transactional
    public RolesVO createRol(User me, RolesVO request) {
        log.info("new rol {}", request.toString());
        Roles nRol = Roles.builder()
                .codrol(request.codrol())
                .descripcion(request.descripcion())
                .build();
        Roles newRol = rolesRepository.save(nRol);        

        for (String codRecurso : request.permisosList()) {
            Recurso recurso = resourceRepository.findByCodrec(codRecurso)
                    .orElseThrow(() -> new DataException("Registro inexistente " + codRecurso));
            recurso.permissioning(newRol);
            profileRepository.save(new Profile(nRol, recurso));
        }

        newRol = rolesRepository.findByCodrol(newRol.getCodrol())
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
            Recurso recurso = resourceRepository.findByCodrec(codRecurso)
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

    /*
     * private void saveRoleApiPermissions(RolesVO newRolesVO) {
     * List<RoleApiEntity> newRoleApiPermList = new ArrayList<>();
     * List<ApiPermissionEntity> newPermApiEntities = newRolesVO.getApiEntities();
     *
     * newPermApiEntities.forEach(entity -> {
     * RoleApiEntity newRoleApiEntity = new RoleApiEntity();
     * newRoleApiEntity.setRoleId(newRolesVO.getRoleId());
     * newRoleApiEntity.setApiPermissionId(entity.getApiPermissionId());
     * newRoleApiPermList.add(newRoleApiEntity);
     * });
     * logger.debug(newRoleApiPermList.toString());
     * this.roleApiRepository.saveAll(newRoleApiPermList);
     * }
     *
     * @Transactional
     * public RolesVO createRole(RolesVO newRolesVO) {
     * // TODO:
     * this.saveRoleApiPermissions(newRolesVO);
     * newRolesVO = this.rolesRepository.save(newRolesVO);
     * return newRolesVO;
     * }
     */
    /**
     *
     * TODO:
     *
     * @param newRolesVO
     * @return
     */

    /*
     * @Transactional
     * public RolesVO updateRole(RolesVO newRolesVO) {
     * // TODO:
     *
     * RolesVO oldEntity =
     * this.roleRepository.findDistinctByRoleId(newRolesVO.getRoleId());
     *
     * this.roleApiRepository.deleteAllByRoleId(newRolesVO.getRoleId());
     * this.saveRoleApiPermissions(newRolesVO);
     *
     * this.roleComponentRepository.deleteAllByRoleId(newRolesVO.getRoleId());
     * this.saveRoleComponentPermissions(newRolesVO);
     *
     * return this.roleRepository.save(newRolesVO);
     * }
     */
    /**
     * TODO:
     *
     * @param roleId
     * @return
     */
    /*
     * @Transactional
     * public Integer deleteRole(Integer roleId) {
     * this.roleApiRepository.deleteAllByRoleId(roleId);
     * this.roleComponentRepository.deleteAllByRoleId(roleId);
     * this.userRoleRepository.deleteAllByRoleId(roleId);
     * return this.roleRepository.deleteByRoleId(roleId);
     * }
     *
     *
     * public Page<RolesVO> getRoleListPaging(RolesVO RolesVO, Pageable pageable) {
     * return this.roleRepository.findAll(new Specification<RolesVO>() {
     *
     * public Predicate toPredicate(Root<RolesVO> root, CriteriaQuery<?>
     * criteriaQuery,
     * CriteriaBuilder criteriaBuilder) {
     * List<Predicate> predicates = new ArrayList<>();
     * if (!StringUtils.isEmpty(RolesVO.getRoleName())) {
     * predicates.add(criteriaBuilder.like(root.get("roleName"), "%" +
     * RolesVO.getRoleName() + "%"));
     * }
     * if (!StringUtils.isEmpty(RolesVO.getStatus())) {
     * predicates.add(criteriaBuilder.equal(root.get("status"),
     * RolesVO.getStatus()));
     * }
     * return criteriaBuilder.and(predicates.toArray(new
     * Predicate[predicates.size()]));
     * }
     * }, pageable);
     * }
     *
     *
     * public RolesVO getRoleById(Integer roleId) {
     * return this.roleRepository.findDistinctByRoleId(roleId);
     * }
     *
     *
     * @Transactional
     * public Integer deleteAuthUsers(Integer roleId, Integer[] userIds) {
     * this.userRoleRepository.deleteByRoleIdAndUserIdIsIn(roleId, userIds);
     * return 0;
     * }
     *
     *
     * @Transactional
     * public Integer addAuthUsers(Integer roleId, Integer[] userIds) {
     * List<UserRolesVO> list = new ArrayList<>();
     * for (int i = 0; i < userIds.length; i++) {
     * UserRolesVO userRolesVO = new UserRolesVO();
     * userRolesVO.setRoleId(roleId);
     * userRolesVO.setUserId(userIds[i]);
     * list.add(userRolesVO);
     * }
     * this.userRoleRepository.saveAll(list);
     * return 0;
     * }
     */

    private Roles findByCodrol(String codrol) {
        return rolesRepository
                .findByCodrol(codrol)
                .orElseThrow(() -> new NoSuchElementException("Rol inexistente para codigo: `%s`".formatted(codrol)));
    }

    private void updateFromVO(Roles r, RolesVO rvo) {
        r.updateDescripcion(rvo.descripcion());
    }
}
