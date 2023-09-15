package gob.gamo.activosf.app.services.sec;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.repository.sec.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;

    @Transactional
    public void deleteRolRecurso(Integer codrol, Integer codrecurso) {
        Optional<Profile> profile = repository.findByRolIdRecursoId(codrol, codrecurso);
        repository.delete(profile.get());
    }    

    @Transactional
    public void deleteByRol(Integer codrol) {
        List<Profile> profiles = repository.findByIdRolId(codrol);
        int c = profiles.size();
        for(Profile p : profiles) {
            repository.delete(p);
        }
        log.info("Profiles Eliminados {}", c);
    }        
}
