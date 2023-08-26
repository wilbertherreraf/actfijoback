package gob.gamo.activosf.app.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;

// @Component
public class AppRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRequestInterceptor.class);

    //    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String postData;
        HttpServletRequest requestCacheWrapperObject = null;
        try {
            // Uncomment to produce the stream closed issue
            // postData = RequestLoggingUtil.getStringFromInputStream(request.getInputStream());

            // To overcome request stream closed issue
            requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
            requestCacheWrapperObject.getParameterMap();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            postData = RequestLoggingUtil.readPayload(requestCacheWrapperObject);
            LOGGER.info("REQUEST DATA: " + postData);
        }
        return true;
    }

    //    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LOGGER.info("RESPONSE: " + response.getStatus());
    }
}
