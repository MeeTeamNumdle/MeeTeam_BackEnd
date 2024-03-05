package synk.meeteam.domain.common.skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;
import synk.meeteam.domain.common.skill.repository.SkillRepository;

@DataJpaTest
@ActiveProfiles("test")
public class SkillRepositoryTest {
    @Autowired
    private SkillRepository skillRepository;

    @PersistenceContext
    private EntityManager em;

//    @BeforeEach
//    void setup() {
//        List<Skill> skills = SkillFixtures.createSkills();
//        skills.forEach((skill) -> em.persist(skill));
//    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는공백limit는5일때() {
        //given
        String keyword = "";
        long limit = 5;

        //when
        List<SkillDto> skills = skillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
//        assertThat(skills)
//                .extracting("name")
//                .containsExactlyInAnyOrder("swift", "react", "kotlin", "python", "spring");
        assertThat(skills.size()).isEqualTo(5);
    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는자바limit는1일때() {
        //given
        String keyword = "자바";
        long limit = 1;

        //when
        List<SkillDto> skills = skillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").containsExactly("자바");
    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는자바limit는5일때() {
        //given
        String keyword = "자바";
        long limit = 5;

        //when
        List<SkillDto> skills = skillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").containsExactly("자바", "자바스크립트");
        assertThat(skills.size()).isEqualTo(2);
    }

    @Test
    public void 스킬목록조회_빈배열반환_keyword는객체limit는5일때() {
        //given
        String keyword = "객체";
        long limit = 5;

        //when
        List<SkillDto> skills = skillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").isEmpty();
        assertThat(skills.size()).isEqualTo(0);
    }

    @Test
    void 특정Skill조회_특정Skill반환_유효한Id입력값() {
        // given
        Long input = 1L;

        // when
        Skill skill = skillRepository.findByIdOrElseThrowException(input);

        // then
        assertThat(skill).isNotNull();
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 0L;

        // when, then
        assertThatThrownBy(() -> skillRepository.findByIdOrElseThrowException(input))
                .isInstanceOf(SkillException.class);
    }
}
