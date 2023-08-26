package gob.gamo.activosf.app.handlers;

import java.io.IOException;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(this.body);
    }

    /*     @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    } */
}
