package gob.gamo.activosf.app.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResponseError {
    public static void createError(Exception e, HttpServletRequest req, HttpServletResponse res) {}
}
