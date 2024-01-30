package synk.meeteam.domain.spec.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.spec.entity.Spec;

public interface SpecRepository extends JpaRepository<Spec, Long> {
    Optional<Spec> findByName(String specName);
}
