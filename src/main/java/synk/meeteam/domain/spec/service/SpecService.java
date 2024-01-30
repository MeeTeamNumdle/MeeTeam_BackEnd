package synk.meeteam.domain.spec.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.spec.entity.Spec;
import synk.meeteam.domain.spec.repository.SpecRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpecService {
    private final SpecRepository specRepository;

    // List<role-count-specs>
    @Transactional
    public List<CreateRecruitmentRoleAndSpecDto> crateRecruitmentMemberSpecs(List<CreateRecruitmentRoleAndSpecDto> recruitmentMemberSpecRequestDtos) {
        List<CreateRecruitmentRoleAndSpecDto> recruitmentMemberSpecResponseDtos = new ArrayList<>();

        recruitmentMemberSpecRequestDtos.stream().forEach(recruitmentMemberRole ->{
            List specs = createSpecs(recruitmentMemberRole.specNames());
            recruitmentMemberSpecResponseDtos.add(
                    CreateRecruitmentRoleAndSpecDto.ofSpecs((Role) recruitmentMemberRole.role(),
                            recruitmentMemberRole.count(), specs));
        });

        return recruitmentMemberSpecResponseDtos;
    }

    private List<Spec> createSpecs(List<String> specNames) {
        List<Spec> specs = new ArrayList<>();

        for(String specName : specNames){
            Spec spec = specRepository.findByName(specName).orElse(null);
            if (spec == null) {
                Spec newSpec = Spec.builder()
                        .name(specName)
                        .build();
                specRepository.save(newSpec);
                specs.add(newSpec);
                continue;
            }
            specs.add(spec);
        }

        return specs;
    }
}
