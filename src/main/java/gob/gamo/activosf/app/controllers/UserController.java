package gob.gamo.activosf.app.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserResponse;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.services.sec.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/users")
    public ModelAndView signUp(@RequestBody SignUpUserRequest request, HttpServletRequest httpServletRequest) {
        log.info("inicio signup {} {}",request.email(), request.nombres());
        userService.signUp(request);

        // Redirect to login API to automatically login when signup is complete
        LoginUserRequest loginRequest = new LoginUserRequest(request.email(), request.password());
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/api/users/login", "user", Map.of("user", loginRequest));
    }

    @ResponseStatus(CREATED)
    @PostMapping("/api/users/login")
    public UserResponse login(@RequestBody LoginUserRequest request) {
        log.info("inicio a login {}",request.toString());
        UserVO userVO = userService.login(request);
        return new UserResponse(userVO);
    }

    @GetMapping("/api/user")
    public UserResponse getCurrentUser(User me) {
        UserVO userVO = new UserVO(me);
        return new UserResponse(userVO);
    }
/*
    @PutMapping("/api/user")
    public UserResponse updateCurrentUser(User me, @RequestBody UpdateUserRequest request) {
        UserVO userVO = userService.update(me, request);
        return new UserResponse(userVO);
    }
    */
}
