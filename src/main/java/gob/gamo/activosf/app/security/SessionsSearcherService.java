package gob.gamo.activosf.app.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.config.AppProperties;

@Slf4j
@Service
public class SessionsSearcherService {
    private LoadingCache<String, String> sessionsCache;

    @Autowired
    private AppProperties appProperties;

    @PostConstruct
    public void init() {
        Integer timeout = (int) appProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        Integer timeoutMin = timeout;
        timeoutMin = timeoutMin + (timeout / 2);
        initCache(timeoutMin, TimeUnit.SECONDS);
    }

    public void initCache(Integer timeSessionLive, TimeUnit timeUnit) {
        if (sessionsCache != null) {
            sessionsCache.invalidateAll();
        }

        sessionsCache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(timeSessionLive, timeUnit)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(final String key) {
                        log.info("load session key {}", key);
                        return key;
                    }
                });

        log.info("Session cache creado con {} {} de vida", timeSessionLive, timeUnit.toString());
    }

    public void createSession(String idsession, String valorCache) {
        if (StringUtils.isBlank(valorCache)) {
            throw new RuntimeException("Valor sesión nulo, no se puede crear");
        }
        sessionsCache.put(idsession, valorCache);
        log.info("Session creada {} con valor {} size {}", idsession, valorCache, sessionsCache.size());
    }

    public void closeSession(String idsession) {
        if (StringUtils.isBlank(idsession)) {
            return;
        }
        sessionsCache.invalidate(idsession);
        log.info("Session cerrada {} size {}", idsession, sessionsCache.size());
    }

    public void revalidateSession(String idsession, String valorSession) {
        if (StringUtils.isBlank(idsession)) {
            return;
        }
        String valorSessionOld = sessionsCache.getIfPresent(idsession);
        if (valorSessionOld != null) {
            sessionsCache.refresh(idsession);
            log.info("Session revalidada {} para {},  size {}", idsession, valorSession, sessionsCache.size());            
        }
        // sessionsCache.put(idsession, valorSession);
    }

    public void validateSession(String idsession, String valorCache) {
        log.info("validateSession {} para valorCache {} ", idsession, valorCache);
        if (StringUtils.isBlank(idsession)) {
            throw new RuntimeException("Codigo de sesión nulo");
        }
        String valorSession = sessionsCache.getIfPresent(idsession);
        if (StringUtils.isBlank(valorSession)) {
            sessionsCache.invalidate(idsession);
            throw new RuntimeException("Codigo de sesión " + idsession + " inválido o no fue registrado anteriormente");
        }

        if (!valorSession.equals(valorCache)) {
            throw new RuntimeException(
                    "Codigo de sesión " + valorSession + " no corresponde a la aplicación " + valorCache);
        }
    }

    public boolean existsSession(String idsession) {
        //log.info("existsSession {} para valorCache {} ", idsession);
        if (StringUtils.isBlank(idsession)) {
            return false;
        }
        String valorSession = sessionsCache.getIfPresent(idsession);
        log.info("valorSessionvalorSession " + valorSession);

        return !StringUtils.isBlank(valorSession); // valorSession.equals(valorCache);
    }

    public String valueCache(String idSession) {
        try {
            return sessionsCache.get(idSession);
        } catch (ExecutionException e) {
            throw new RuntimeException("Codigo de sesión inexistente " + idSession);
        }
    }
}
