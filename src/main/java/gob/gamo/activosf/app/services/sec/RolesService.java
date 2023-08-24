package gob.gamo.activosf.app.services.sec;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final RecursoRepository resourceRepository;

    @Transactional(readOnly = true)
    public RolesVO getSingleRol(User me, String codrol) {
        Roles article = findByCodrol(codrol);
        return new RolesVO(article);
    }

    @Transactional(readOnly = true)
    public List<RolesVO> getRoles(User me, Pageable pageable) {
        log.info("en get roles-->");
        Page<Roles> list = rolesRepository.findAll(pageable);
        log.info("en get roles size {}", list.getContent().size());
        return list.getContent().stream().map(r -> new RolesVO(r)).toList();
    }

    /*
     * @Transactional(readOnly = true)
     * public List<RolesVO> getArticles(User me, ArticleFacets facets) {
     * String tag = facets.tag();
     * String author = facets.author();
     * String favorited = facets.favorited();
     * Pageable pageable = facets.getPageable();
     *
     * Page<Article> byFacets = rolesRepository.findByFacets(tag, author, favorited,
     * pageable);
     * return byFacets.getContent().stream()
     * .map(article -> new ArticleVO(me, article))
     * .toList();
     * }
     */
    @Transactional
    public RolesVO createRol(User me, RolesVO request) {
        Roles newRol = Roles.builder()
                .codrol(request.codrol())
                .descripcion(request.descripcion())
                .build();

        for (Recurso invalidTag : request.permisos()) {
            Optional<Recurso> optionalTag = resourceRepository.findByCodrec(invalidTag.getCodrec());
            Recurso validTag = optionalTag.orElseGet(() -> resourceRepository.save(invalidTag));
            validTag.permissioning(newRol);
        }
        log.info("rolnewwwww: {} {}", newRol.getCodrol(), newRol.getDescripcion());
        newRol = rolesRepository.save(newRol);
        return new RolesVO(newRol);
    }

    @Transactional
    public RolesVO updateRol(User me, String codrol, RolesVO request) {
        Roles rol = rolesRepository
                .findByCodrol(codrol)
                .orElseThrow(() -> new NoSuchElementException("Rol inexistente para codigo: `%s`".formatted(codrol)));

        updateFromVO(rol, request);
        return new RolesVO(rol);
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
