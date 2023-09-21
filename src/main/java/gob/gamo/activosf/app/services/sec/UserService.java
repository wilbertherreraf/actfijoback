package gob.gamo.activosf.app.services.sec;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import gob.gamo.activosf.app.config.JwtTokenProvider;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.repository.sec.UserRepository;

/**
 * UserService
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider bearerTokenSupplier;

    @Transactional
    public User signUp(SignUpUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email(`%s`) already exists.".formatted(request.email()));
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username(`%s`) already exists.".formatted(request.username()));
        }

        User newUser = this.createNewUser(request);
        return userRepository.save(newUser);
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
    public Page<UserVO> findAll(Pageable pageable) {
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
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
        /*
         * user.updateBio(request.bio());
         * user.updateImage(request.image());
         */

        return new UserVO(user);
    }

    private User createNewUser(SignUpUserRequest request) {
        return User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .nombres(request.nombres())
                .build();
    }
}
