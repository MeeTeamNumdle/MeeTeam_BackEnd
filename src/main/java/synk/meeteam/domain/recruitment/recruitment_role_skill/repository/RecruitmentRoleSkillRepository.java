package synk.meeteam.domain.recruitment.recruitment_role_skill.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;

public interface RecruitmentRoleSkillRepository extends JpaRepository<RecruitmentRoleSkill, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from RecruitmentRoleSkill r where r.recruitmentRole.id in :recruitmentRoleIds")
    void deleteAllByRecruitmentRoleIdInQuery(@Param("recruitmentRoleIds") List<Long> recruitmentRoleIds);
}
