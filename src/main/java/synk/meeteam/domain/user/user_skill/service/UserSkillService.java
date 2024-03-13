package synk.meeteam.domain.user.user_skill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;
import synk.meeteam.domain.user.user_skill.repository.UserSkillRepository;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public void updateUserSkillsByIds(User user, List<Long> skillIds) {
        //기존 유저 스킬 모두 삭제
        userSkillRepository.deleteAllByUser(user);
        //유저 스킬 목록 조회
        List<Skill> skills = skillRepository.findAllById(skillIds);
        //유저 스킬로 변환
        List<UserSkill> userSkills = skills.stream().map(skill -> new UserSkill(user, skill)).toList();
        //유저 스킬 목록 저장
        userSkillRepository.saveAll(userSkills);
    }
}
