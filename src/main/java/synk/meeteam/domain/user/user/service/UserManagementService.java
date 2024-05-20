package synk.meeteam.domain.user.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.portfolio.portfolio_link.repository.PortfolioLinkRepository;
import synk.meeteam.domain.portfolio.portfolio_skill.repository.PortfolioSkillRepository;
import synk.meeteam.domain.recruitment.bookmark.repository.BookmarkRepository;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;
import synk.meeteam.domain.recruitment.recruitment_role_skill.repository.RecruitmentRoleSkillRepository;
import synk.meeteam.domain.recruitment.recruitment_tag.repository.RecruitmentTagRepository;
import synk.meeteam.domain.user.award.repository.AwardRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.domain.user.user_link.repository.UserLinkRepository;
import synk.meeteam.domain.user.user_skill.repository.UserSkillRepository;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    // 유저 관련
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final UserLinkRepository userLinkRepository;
    private final AwardRepository awardRepository;

    // 포토폴리오 관련
    private final PortfolioRepository portfolioRepository;
    private final PortfolioSkillRepository portfolioSkillRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;

    // 구인 관련
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final RecruitmentRoleRepository recruitmentRoleRepository;
    private final RecruitmentRoleSkillRepository recruitmentRoleSkillRepository;
    private final RecruitmentTagRepository recruitmentTagRepository;
    private final RecruitmentCommentRepository recruitmentCommentRepository;
    private final RecruitmentApplicantRepository recruitmentApplicantRepository;

    // 기타
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void deleteUser(User user) {
        recruitmentPostRepository.findAllByCreatedBy(user.getId())
                .forEach(this::deleteRecruitmentPost);

        portfolioRepository.findAllByCreatedBy(user.getId())
                .forEach(portfolio -> deletePortfolio(portfolio, user));

        deleteProfile(user);

        userRepository.delete(user);
    }

    private void deleteRecruitmentPost(RecruitmentPost recruitmentPost) {
        List<Long> recruitmentRoleIds = recruitmentRoleRepository.findByPostIdWithSkills(
                recruitmentPost.getId()).stream()
                .map(RecruitmentRole::getId).toList();

        recruitmentRoleSkillRepository.deleteAllByRecruitmentRoleIdInQuery(recruitmentRoleIds);
        recruitmentRoleRepository.deleteAllByRecruitmentPostId(recruitmentPost.getId());
        recruitmentTagRepository.deleteAllByRecruitmentPostId(recruitmentPost.getId());
        recruitmentCommentRepository.deleteAllByRecruitmentPost(recruitmentPost);
        recruitmentApplicantRepository.deleteAllByRecruitmentPost(recruitmentPost);
        recruitmentPostRepository.delete(recruitmentPost);
    }

    private void deletePortfolio(Portfolio portfolio, User user){
        portfolioSkillRepository.deleteAllByPortfolio(portfolio);
        portfolioLinkRepository.deleteAllByPortfolio(portfolio);
        awardRepository.deleteAllByCreatedBy(user.getId());
        bookmarkRepository.deleteAllByUser(user);
        portfolioRepository.delete(portfolio);
    }

    private void deleteProfile(User user){
        userSkillRepository.deleteAllByCreatedBy(user.getId());
        userLinkRepository.deleteAllByCreatedBy(user.getId());
    }
}
