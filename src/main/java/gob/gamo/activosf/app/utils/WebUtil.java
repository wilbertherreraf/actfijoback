package gob.gamo.activosf.app.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author itmuch.com
 */
public class WebUtil {
    /**
     *
     *
     * @param cookies    cookie
     * @param cookieName cookieName
     * @return cookie
     */
    public static Optional<Cookie> filterCookieByName(Cookie[] cookies, String cookieName) {
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> Objects.equals(c.getName(), cookieName))
                .findFirst();
    }

    /**
     * request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if ((requestAttributes == null)) {
            throw new RuntimeException("requestAttributes null");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getBaseURL() {
        final HttpServletRequest request = getRequest();

        return request.getRequestURL().toString().replace(request.getRequestURI(), "/");
    }

    /**
     * Get the actual date as {@link String}
     *
     * @return actual date in {@link String} formatted in dd/MM/yyyy
     */
    public static String getActualDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
    }

    /**
     * Get the actual date and time as {@link String}
     *
     * @return actual date and time as {@link String} formatted in dd/MM/yyyy HH:mm
     */
    public static String getActualDateTime() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(LocalDateTime.now());
    }

    /**
     * Get the current year to use at the little notice at the application footer
     *
     * @return current year for the copyright text
     */
    public static String getCurrentYear() {
        return DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
    }
}
