package gob.gamo.activosf.app.services.sec;

import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.config.JwtTokenProvider;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.domain.entities.Userrol;
import gob.gamo.activosf.app.domain.entities.UserrolId;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.repository.sec.UserRolRespository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.UserSpecification;

/**
 * UserService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider bearerTokenSupplier;
    private final RolesRepository rolesRepository;
    private final UserRolRespository userRolRespository;

    @Transactional
    public User signUp(SignUpUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email(`%s`) ya fue registrado.".formatted(request.email()));
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username(`%s`) ya fue registrado.".formatted(request.username()));
        }

        User newUser = this.createNewUser(request);
        normalizer(newUser);
        validar(newUser);
        User newU = userRepository.save(newUser);
        log.info("Ususario creado {} UN: {} nombre: {}", newU.getId(), newU.getUsername(), newU.getNombres());
        return newU;
    }

    @Transactional(readOnly = true)
    public UserVO login(LoginUserRequest request) {
        return userRepository
                .findByUsername(request.username())
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    String token = bearerTokenSupplier.supply(user);
                    return new UserVO(user.possessToken(token));
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    @Transactional(readOnly = true)
    public Page<UserVO> findAll(String searchTxt, Pageable pageable) {
        CriteriaParser parser = new CriteriaParser();
        Deque<?> deque = parser.parse(searchTxt);
        if (deque.size() > 0) {
            GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<User> spec = specBuilder.build(deque, UserSpecification::new);
            Page<UserVO> list0 = userRepository.findAll(spec, pageable).map(r -> new UserVO(r));
            return list0;
        }
        Page<UserVO> list = userRepository.findAll(pageable).map(r -> new UserVO(r));
        return list;
    }

    @Transactional(readOnly = true)
    public List<User> getusers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserVO getuser(String username) {
        UserVO userVO = userRepository
                .findByUsername(username)
                .map(user -> {
                    String token = bearerTokenSupplier.supply(user);
                    return new UserVO(user.possessToken(token));
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password." + username));
        return userVO;
    }

    @Transactional
    public UserVO update(User user, UpdateUserRequest request) {
        String email = request.email();
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email(`%s`) already exists.".formatted(email));
        }

        String username = request.username();
        if (!user.getUsername().equals(username) && userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username(`%s`) already exists.".formatted(request.username()));
        }

        user.updateEmail(email);
        user.updateUsername(username);
        user.updatePassword(passwordEncoder, request.password());
        return new UserVO(user);
    }

    @Transactional
    public UserVO updateUser(UpdateUserRequest request) {

        User unew = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new DataException("Usuario inexistente " + request.username()));
        unew.setIdUnidEmpl(request.idPersona());
        unew.setNombres(request.nombres());
        unew.setCodPersona(request.codpersona());
        unew.setEmail(request.email());
        normalizer(unew);
        validar(unew);
        User un = userRepository.save(unew);

        return new UserVO(un);
    }

    @Transactional
    public UserVO updateRoles(UpdateUserRequest request) {
        User unew = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new DataException("Usuario inexistente " + request.username()));

        for (RolesVO r : request.roles()) {
            Roles rol = rolesRepository
                    .findByCodrol(r.codrol())
                    .orElseThrow(() -> new DataException("Rol inexistente " + r.codrol()));
            Optional<Roles> exists = unew.getRoles().stream()
                    .filter(ru -> ru.getCodrol().equalsIgnoreCase(r.codrol()))
                    .findFirst();
            if (!exists.isPresent()) {
                Userrol userr = new Userrol(unew, rol);
                unew.getRoles().add(rol);
                /*
                 * Userrol userrn = userRolRespository.save(userr);
                 * log.info("User rol {} {}",
                 * userrn.getId().getUserId(),userrn.getId().getRolId());
                 */
            }
        }
        Optional<User> userReq = userRepository.findByUsername(request.username());
        log.info(
                "User USERNAME [{}] {} roles :> {}",
                userReq.get().getId(),
                userReq.get().getUsername(),
                userReq.get().getRoles().stream()
                        .map(Roles::getCodrol)
                        .collect(Collectors.toList())
                        .toString());
        UserVO userVO = userRepository
                .findByUsername(request.username())
                .map(u -> new UserVO(u))
                .orElseThrow(() -> new DataException("Usuario inexistente " + request.username()));

        return userVO;
    }

    public void deleteRol(String username, String codrol) {
        User uold = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new DataException("Usuario inexistente " + username));
        Optional<Roles> rol = rolesRepository.findByCodrol(codrol);
        if (rol.isPresent()) {
            Userrol usrrol = userRolRespository
                    .findById(new UserrolId(uold.getId(), rol.get().getId()))
                    .orElseThrow(() -> new DataException("Permiso inexistente " + codrol));
            userRolRespository.delete(usrrol);
        }
    }

    private User createNewUser(SignUpUserRequest request) {
        return User.builder()
                .email(StringUtils.trimToNull(request.email()))
                .username(StringUtils.trimToNull(request.username()))
                .codPersona(StringUtils.trimToNull(request.codpersona()))
                .password(passwordEncoder.encode(request.password()))
                .nombres(StringUtils.trimToNull(request.nombres()))
                .idUnidEmpl(request.idPersona())
                .build();
    }

    public static void validar(User u) {
        if (u.getUsername() == null) {
            throw new DataException("Username invalido");
        }
    }

    public static void normalizer(User u) {
        u.setUsername(StringUtils.lowerCase(StringUtils.trimToNull(u.getUsername())));
        u.setEmail(StringUtils.lowerCase(StringUtils.trimToNull(u.getEmail())));
        u.setPassword(StringUtils.trimToNull(u.getPassword()));
    }
}
