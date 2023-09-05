package gob.gamo.activosf.app.security.mapper;

import java.util.*;

import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.dto.sec.RolesVO;

public class RoleMapper {

    /* public static RolesVO toRoleDto(Roles role) {
            if (role == null) {
                return null;
            }
            List<PagesPrivileges> pagesPrivilegesList =
                    role.getRolePagesPrivileges()
                            .stream()
                            .map(RolePagesPrivileges::getPagesPrivileges)
                            .toList();


            Map<String, List<String>> map = pagesPrivilegesList.stream().collect(
                    Collectors.groupingBy(
                            pagesPrivileges -> pagesPrivileges.getPage().getName(),
                            Collectors.mapping(
                                    pagesPrivileges -> pagesPrivileges.getPrivilege().getName(),
                                    Collectors.toList()
                            )
                    )
            );


            Map<PageDto, Collection<PrivilegeDto>> pageDtoListMap =
                    map.entrySet().stream().collect(
                            Collectors.toMap(stringListEntry -> new PageDto(stringListEntry.getKey()),
                                    o -> o.getValue().stream().map(PrivilegeDto::new).toList()
                            ));

            RoleDto roleDto = new RoleDto();
            roleDto.setName(role.getName());
            roleDto.setPagePrivilegeMap(pageDtoListMap);
            return roleDto;
        }
    */
    public static Collection<RolesVO> toRoleDtos(Collection<Roles> all) {
        if (all == null) {
            return Collections.emptyList();
        }
        return all.stream().map(x -> new RolesVO(x)).toList();
    }

    /*
    public static Role toRole(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        Collection<PagesPrivileges> pagesPrivilegesList = roleDto.getPagePrivilegeMap()
                .entrySet()
                .stream()
                .map(pageDtoListEntry -> {

                    Page page = PageMapper.toPage(pageDtoListEntry.getKey());
                    List<Privilege> privileges = PrivilegeMapper.toPrivileges(pageDtoListEntry.getValue());

                    return PageMapper.toPagesPrivileges(page, privileges);

                }).toList().stream().flatMap(Collection::stream).toList();

        Role role = new Role(roleDto.getName());

        Collection<RolePagesPrivileges> rolePagesPrivilegesList = pagesPrivilegesList.stream().map(pagesPrivileges -> {
            RolePagesPrivileges rolePagesPrivileges = new RolePagesPrivileges();
            rolePagesPrivileges.setPagesPrivileges(pagesPrivileges);
            rolePagesPrivileges.setRole(role);
            return rolePagesPrivileges;
        }).toList();
        role.setRolePagesPrivileges(rolePagesPrivilegesList);
        return role;
    }


    public static Collection<Role> toRoles(Collection<RoleDto> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream().map(RoleMapper::toRole).toList();
    } */
}
