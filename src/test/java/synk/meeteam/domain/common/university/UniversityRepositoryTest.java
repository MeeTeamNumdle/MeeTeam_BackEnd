package synk.meeteam.domain.common.university;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UniversityRepositoryTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    private UniversityRepository universityRepository;

    @BeforeAll
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            // 자신의 script path 넣어주면 됨
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/init/data.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    // given 생략(when과 겹친다고 생각)
    public void 대학리스트조회_대학리스트반환() {
        List<University> universities = universityRepository.findAll();
        Assertions.assertThat(universities.size()).isNotEqualTo(0);
    }
}
