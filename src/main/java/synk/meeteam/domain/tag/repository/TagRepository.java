package synk.meeteam.domain.tag.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String tagName);
}
