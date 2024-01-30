package synk.meeteam.domain.recruitment_member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;
import synk.meeteam.domain.recruitment.entity.Recruitment;
import synk.meeteam.domain.recruitment_member.entity.RecruitmentMember;
import synk.meeteam.domain.recruitment_member.repository.RecruitmentMemberRepository;
import synk.meeteam.domain.recruitment_member_spec.entity.RecruitmentMemberSpec;
import synk.meeteam.domain.recruitment_member_spec.repository.RecruitmentMemberSpecRepository;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.spec.entity.Spec;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentMemberService {
    private final RecruitmentMemberRepository recruitmentMemberRepository;
    private final RecruitmentMemberSpecRepository recruitmentMemberSpecRepository;

    @Transactional
    public void createRecruitmentMembers(Recruitment recruitment,
                                         List<CreateRecruitmentRoleAndSpecDto> recruitmentRoleAndSpecDtos) {
        // 4. RecruitmentMember 여러개 생성
        // 4-1. 해당 엔티티 만든 후에, 해당 id로 RecruitmentMemberSpec 여러개 생성

        recruitmentRoleAndSpecDtos.stream().forEach(dto -> {
            RecruitmentMember createdRecruitmentMember = RecruitmentMember.builder()
                    .recruitment(recruitment)
                    .role((Role) dto.role())
                    .count(dto.count())
                    .build();
            recruitmentMemberRepository.saveAndFlush(createdRecruitmentMember);
            createRecruitmentMemberSpecs(createdRecruitmentMember, dto.specNames());
        });
    }

    private void createRecruitmentMemberSpecs(RecruitmentMember recruitmentMember, List<Spec> specs){
        for(Spec spec : specs){
            RecruitmentMemberSpec memberSpec = RecruitmentMemberSpec.builder()
                    .recruitmentMember(recruitmentMember)
                    .spec(spec)
                    .build();
            recruitmentMemberSpecRepository.save(memberSpec);
        }
    }
}
