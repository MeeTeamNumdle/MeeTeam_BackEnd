package synk.meeteam.domain.recruitment.recruitment_post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentPostService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public RecruitmentPost createRecruitmentPost(RecruitmentPost recruitmentPost) {
        return recruitmentPostRepository.save(recruitmentPost);
    }
}
