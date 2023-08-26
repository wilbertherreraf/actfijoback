package gob.gamo.activosf.app.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserResponse;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.services.sec.UserService;
import gob.gamo.activosf.app.utils.HeaderUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
// @RequestMapping(Constants.API_URL_ROOT + Constants.API_URL_VERSION)
public class UserController {
    private final UserService userService;
    private static final String ENTITY_NAME = "User";

    @PostMapping("/api/users")
    public ModelAndView signUp(@RequestBody SignUpUserRequest request, HttpServletRequest httpServletRequest) {
        log.info("inicio signup {} {}", request.email(), request.nombres());
        userService.signUp(request);

        // Redirect to login API to automatically login when signup is complete
        LoginUserRequest loginRequest = new LoginUserRequest(request.email(), request.password());
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/api/users/login", "user", Map.of("user", loginRequest));
    }

    @ResponseStatus(CREATED)
    @PostMapping("/api/users/login")
    public UserResponse login(@RequestBody LoginUserRequest request) {
        /*         LoginUserRequest loginRequest = new LoginUserRequest("asdf", "asdf");*/
        log.info("inicio a login {}", request.toString());

        UserVO userVO = userService.login(request);
        return new UserResponse(userVO);
    }

    @GetMapping("/api/user")
    public UserResponse getCurrentUser(User me) {
        log.info("rest get user {}, {}", me.getUsername(), me.getEmail());
        userService.getuser(me.getUsername());
        UserVO userVO = new UserVO(me);
        return new UserResponse(userVO);
    }

    @GetMapping(Constants.API_URL_ROOT + Constants.API_URL_VERSION + "/users")
    public ResponseEntity<List<UserVO>> getAll(Pageable pageable) {
        log.info("en {} query u {}", this.getClass().getSimpleName(), pageable != null ? pageable.toString() : "");
        // List<UserResponse> list = userService.getusers().stream().map(r -> new UserVO(r)).map(r -> new
        // UserResponse(r)).collect(Collectors.toList());
        List<UserVO> list =             userService.getusers().stream().map(r -> new UserVO(r)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/api/user")
    public ResponseEntity<UserResponse> updateCurrentUser(User me, @RequestBody UpdateUserRequest request) {
        log.info("en update user meeeeeeeeeeeeee");
        log.info("en update user me: {}, req: {}", me.getUsername(), request.toString());
        UserVO userVO = userService.update(me, request);
        UserResponse result = new UserResponse(userVO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
                .body(result);
        // return new UserResponse(userVO);
    }
}
