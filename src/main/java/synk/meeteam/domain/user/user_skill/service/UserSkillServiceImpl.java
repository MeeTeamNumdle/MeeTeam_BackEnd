package synk.meeteam.domain.user.user_skill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;
import synk.meeteam.domain.user.user_skill.repository.UserSkillRepository;

@Service
@RequiredArgsConstructor
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public List<UserSkill> changeUserSkillBySkill(Long userId, List<Long> skillIds) {
        //기존 유저 스킬 모두 삭제
        userSkillRepository.deleteAllByCreatedBy(userId);
        userSkillRepository.flush();
        //유저 스킬 목록 조회
        List<Skill> skills = skillRepository.findAllById(skillIds);
        //유저 스킬로 변환
        List<UserSkill> userSkills = skills.stream().map(UserSkill::new).toList();
        //유저 스킬 목록 저장
        return userSkillRepository.saveAll(userSkills);
    }

    @Override
    public List<SkillDto> getUserSKill(Long userId) {
        return userSkillRepository.findSkillDtoByCreatedBy(userId);
    }
}
