package synk.meeteam.domain.common.university;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.common.university.service.UniversityService;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceTest {

    @InjectMocks
    UniversityService universityService;
    @Spy
    UniversityRepository universityRepository;

    @Test
    // given 생략(when과 겹친다고 생각)
    public void 대학리스트조회_대학리스트반환() {
        // given
        doReturn(UniversityFixture.createUniversities()).when(universityRepository).findAll();

        // when
        List<University> universities = universityService.getUniversities();

        // then
        assertThat(universities.size()).isNotEqualTo(0);
        assertThat(universities.get(0).getId()).isNotNull();
        assertThat(universities.get(0).getName()).isNotNull();
        assertThat(universities.get(0).getEmailRegex()).isNotNull();
    }
}
