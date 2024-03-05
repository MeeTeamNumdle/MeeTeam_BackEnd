package synk.meeteam.domain.common.university;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UniversityRepositoryTest {
    @Autowired
    private UniversityRepository universityRepository;

    @Test
    // given 생략(when과 겹친다고 생각)
    public void 대학리스트조회_대학리스트반환() {
        List<University> universities = universityRepository.findAll();
        Assertions.assertThat(universities.size()).isNotEqualTo(0);
    }
}
