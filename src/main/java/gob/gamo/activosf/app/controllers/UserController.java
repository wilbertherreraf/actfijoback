package gob.gamo.activosf.app.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.config.JwtTokenProvider;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserResponse;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.errors.ResourceNotFoundException;
import gob.gamo.activosf.app.errors.TokenRefreshException;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.security.SessionsSearcherService;
import gob.gamo.activosf.app.services.sec.UserService;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_ROOT_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
// @RequestMapping(Constants.API_URL_ROOT + Constants.API_URL_VERSION)
public class UserController {
    private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^Token (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);
    private final UserRepository userRepository;
    private final UserService userService;
    private final SessionsSearcherService sessionsSearcherService;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String ENTITY_NAME = Constants.REC_USUARIOS;

    @ResponseStatus(CREATED)
    @PostMapping(Constants.API_LOGIN)
    public ResponseEntity<UserResponse> login(@RequestBody LoginUserRequest request) {
        log.info("inicio a login {}", request.toString());

        UserVO userVO = userService.login(request);
        UserResponse userResponse = new UserResponse(userVO);
        HttpHeaders headers = new HttpHeaders();
        String tokenRefresh = jwtTokenProvider.getRefreshToken(userResponse.user().token());
        headers.add(Constants.HEADER_X_ACTIVOS, tokenRefresh);
        return ResponseEntity.ok().headers(headers).body(userResponse);
    }

    // @PostMapping("/api/users")
    @PostMapping(Constants.API_PUBLIC + "/register")
    public ModelAndView registroUsuario(@RequestBody SignUpUserRequest request, HttpServletRequest httpServletRequest) {
        log.info("inicio signup {} {}", request.email(), request.nombres());
        userService.signUp(request);

        LoginUserRequest loginRequest = new LoginUserRequest(request.email(), request.password());
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView(
                "redirect:" + Constants.API_ROOT_VERSION + Constants.API_LOGIN, "user", Map.of("user", loginRequest));
    }

    /********************************************** */
    // @PostMapping("/api/user/refreshtoken")
    @PostMapping(Constants.API_USUARIOS + "/refreshtoken")
    public ResponseEntity<UserResponse> refreshToken(HttpServletRequest request) {
        String username = resolveRefreshTokenHeader(request);
        if (StringUtils.isBlank(username)) {
            throw new TokenRefreshException("", "Token Refresh invalido");
        }
        log.info("en refreshToken username: {}, req: {}", username);

        UserVO userVO = userService.getuser(username);
        UserResponse userResponse = new UserResponse(userVO);
        HttpHeaders headers = new HttpHeaders();
        String tokenRefresh = jwtTokenProvider.getRefreshToken(userResponse.user().token());
        headers.add(Constants.HEADER_X_ACTIVOS, tokenRefresh);
        return ResponseEntity.ok().headers(headers).body(userResponse);
        // return new UserResponse(userVO);
    }

    @GetMapping(Constants.API_USUARIOS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<UserVO>> getAll(@RequestParam(value = "q", required = false) String search,
            Pageable pageable) {
        final Page<UserVO> page = userService.findAll(search, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_UNIDS);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping(Constants.API_USUARIOS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<UserResponse> nuevoUsuario(
            @RequestBody SignUpUserRequest request, HttpServletRequest httpServletRequest) {
        User newuser = userService.signUp(request);
        UserVO userVO = userService.getuser(newuser.getUsername());
        UserResponse result = new UserResponse(userVO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(Constants.API_USUARIOS + "/{username}")
    @PreAuthorize("#username == authentication.name or hasAuthority('" + ENTITY_NAME + "')")
    public UserResponse getUsuario(User me, @PathVariable(value = "username") String username) {
        UserVO userVO = userService.getuser(username);

        return new UserResponse(userVO);
    }

    // @PutMapping("/api/user")
    @PutMapping(Constants.API_USUARIOS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<UserResponse> updateUser(
            User me, @PathVariable(value = "slug") String id, @RequestBody UpdateUserRequest request) {
        UserVO userVO = userService.updateUser(request);
        UserResponse result = new UserResponse(userVO);
        return ResponseEntity.ok().body(result);
    }

    // update for owner account
    @PutMapping(Constants.API_USUARIOS)
    public ResponseEntity<UserResponse> updateCurrentUser(User me, @RequestBody UpdateUserRequest request) {
        UserVO userVO = userService.update(me, request);
        UserResponse result = new UserResponse(userVO);
        return ResponseEntity.ok().body(result);
    }

    /* ROLES USER */
    @GetMapping(Constants.API_USUARIOS + "/{username}" + Constants.API_ROLES)
    @PreAuthorize("#username == authentication.name or hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<RolesVO>> usuarioRoles(
            @PathVariable(value = "username") String username, Pageable pageable) {
        User result = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataException("Registro inexistente " + username));
        Set<Roles> empl = result.getRoles();
        Page<Roles> page = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent().stream().map(r -> new RolesVO(r)).toList());
    }

    @PutMapping(Constants.API_USUARIOS + "/{username}" + Constants.API_ROLES)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<UserResponse> updUsuarioRoles(User me,
            @PathVariable(value = "username") String username, @RequestBody UpdateUserRequest request) {

        UserVO userVO = userService.updateRoles(request);

       UserResponse result = new UserResponse(userVO);
        return ResponseEntity.ok().body(result);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void postNotFound() {
    }

    private String resolveRefreshTokenHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            throw new IllegalArgumentException("Token Auth inexistente");
        }
        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            throw new TokenRefreshException("", "Token Refresh is malformed");
        }
        String token = matcher.group("token");
        String tokenRefresh = jwtTokenProvider.getRefreshToken(token);
        boolean existToken = sessionsSearcherService.existsSession(tokenRefresh);
        if (!existToken) {
            String username = jwtTokenProvider
                    .getSubFromToken(token)
                    .orElseThrow(() -> new TokenRefreshException(token, "Token expirado"));
            return username;
        } else {
            String userRefresh = sessionsSearcherService.valueCache(tokenRefresh);
            Gson gson = new Gson();
            UserVO userVORefresh = gson.fromJson(userRefresh, UserVO.class);
            log.info("user map {}", userVORefresh.toString());

            sessionsSearcherService.closeSession(token);
            return userVORefresh.username();
        }
    }
}
