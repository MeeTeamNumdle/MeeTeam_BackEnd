package synk.meeteam.domain.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

}
