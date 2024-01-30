package synk.meeteam.domain.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.entity.Recruitment;
import synk.meeteam.domain.recruitment.repository.RecruitmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment createRecruitment(CreateRecruitmentPostRequestDto requestDto){
        Recruitment recruitmentPost = Recruitment.builder()
                .meeteam(requestDto.meeteam())
                .title(requestDto.title())
                .content(requestDto.content())
                .deadline(requestDto.deadline())
                .build();
        return recruitmentRepository.save(recruitmentPost);
    }
}
