package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadService {
    private final OrgUnidadRepository orgUnidadRepository;

    @Transactional(readOnly = true)
    public List<UnidadResponse> findAll() {
        List<UnidadResponse> list = orgUnidadRepository.findAll().stream()
                .map(r -> new UnidadResponse(r))
                .collect(Collectors.toList());
        return list;
    }
}
