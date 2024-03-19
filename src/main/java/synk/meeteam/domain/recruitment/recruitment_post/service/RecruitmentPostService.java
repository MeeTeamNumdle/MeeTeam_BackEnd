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
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost) {
        return recruitmentPostRepository.save(recruitmentPost);
    }

    @Transactional(readOnly = true)
    public RecruitmentPost getRecruitmentPost(final Long postId) {
        return recruitmentPostRepository.findByIdOrElseThrow(postId);
    }

    @Transactional
    public RecruitmentPost closeRecruitment(Long postId, Long userId) {
        RecruitmentPost recruitmentPost = getRecruitmentPost(postId);

        // 작성자인지 확인
        recruitmentPost.validateWriter(userId);

        // 구인 마감 설정
        recruitmentPost.closeRecruitmentPost();

        return recruitmentPost;

    }
}
