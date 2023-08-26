package gob.gamo.activosf.app.handlers;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomeRequestLoggingFilter extends CommonsRequestLoggingFilter {

    public CustomeRequestLoggingFilter() {
        log.info("en crear custome oggien fileter");
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(10000);
    }
}
