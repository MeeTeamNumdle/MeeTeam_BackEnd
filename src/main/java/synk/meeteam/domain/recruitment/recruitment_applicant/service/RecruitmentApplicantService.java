package synk.meeteam.domain.recruitment.recruitment_applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantService {
    private final RecruitmentApplicantRepository recruitmentApplicantRepository;

    @Transactional
    public void registerRecruitmentApplicant(RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicantRepository.save(recruitmentApplicant);
    }
}
