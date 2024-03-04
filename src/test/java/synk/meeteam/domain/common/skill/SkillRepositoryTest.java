package synk.meeteam.domain.common.skill;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SKillRepository;

@SpringBootTest
@Transactional
public class SkillRepositoryTest {
    @Autowired
    private SKillRepository sKillRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setup() {
        List<Skill> skills = SkillFixtures.createSkills();
        skills.forEach((skill) -> em.persist(skill));
    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는공백limit는5일때() {
        //given
        String keyword = "";
        long limit = 5;

        //when
        List<SkillDto> skills = sKillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").containsExactlyInAnyOrder("자바", "자바스크립트", "파이썬", "스프링", "리엑트");
        assertThat(skills.size()).isEqualTo(5);
    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는자바limit는1일때() {
        //given
        String keyword = "자바";
        long limit = 1;

        //when
        List<SkillDto> skills = sKillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").containsExactly("자바");
    }

    @Test
    public void 스킬목록조회_스킬목록Dto반환_keyword는자바limit는5일때() {
        //given
        String keyword = "자바";
        long limit = 5;

        //when
        List<SkillDto> skills = sKillRepository.findAllByKeywordAndTopLimit(keyword, limit);

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
        List<SkillDto> skills = sKillRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(skills).extracting("name").isEmpty();
        assertThat(skills.size()).isEqualTo(0);
    }
}
