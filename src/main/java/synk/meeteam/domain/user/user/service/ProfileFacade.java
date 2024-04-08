package synk.meeteam.domain.user.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.entity.PortfolioMapper;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.user.award.dto.GetProfileAwardDto;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.award.entity.AwardMapper;
import synk.meeteam.domain.user.award.service.AwardService;
import synk.meeteam.domain.user.user.dto.ProfileMapper;
import synk.meeteam.domain.user.user.dto.UpdateProfileCommandMapper;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.dto.GetProfileUserLinkDto;
import synk.meeteam.domain.user.user_link.entity.UserLink;
import synk.meeteam.domain.user.user_link.entity.UserLinkMapper;
import synk.meeteam.domain.user.user_link.service.UserLinkService;
import synk.meeteam.domain.user.user_skill.service.UserSkillService;
import synk.meeteam.infra.s3.S3FileName;
import synk.meeteam.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class ProfileFacade {

    private final UserService userService;
    private final UserLinkService userLinkService;
    private final UserSkillService userSkillService;
    private final PortfolioService portfolioService;
    private final AwardService awardService;
    private final S3Service s3Service;

    private final UpdateProfileCommandMapper updateProfileCommandMapper;

    private final UserLinkMapper userLinkMapper;
    private final AwardMapper awardMapper;
    private final PortfolioMapper portfolioMapper;
    private final ProfileMapper profileMapper;

    @Transactional
    public void editProfile(User user, UpdateProfileRequestDto profileDto) {
        //유저 닉네임 덮어쓰기
        userService.changeOrKeepNickname(user, profileDto.nickname());

        //프로필 유저 정보 업데이트
        userService.changeUserInfo(user, updateProfileCommandMapper.toUpdateProfileCommand(profileDto));

        //유저 링크 덮어쓰기
        userLinkService.changeUserLink(user.getId(), profileDto.links());

        //유저 스킬 덮어쓰기
        userSkillService.changeUserSkillBySkill(user.getId(), profileDto.skills());

        //수상 활동 내역 덮어쓰기
        awardService.changeAward(user.getId(), profileDto.awards());

        //핀 설정
        portfolioService.changePinPortfoliosByIds(user.getId(), profileDto.portfolios());
    }

    @Transactional(readOnly = true)
    public GetProfileResponseDto readProfile(String encryptedId) {
        User user = userService.findByEncryptedId(encryptedId);
        String profileImgUrl = s3Service.createPreSignedGetUrl(S3FileName.USER, user.getProfileImgFileName());
        List<GetProfileUserLinkDto> links = getProfileLinks(user.getId());
        List<GetProfileAwardDto> awards = getProfileAwards(user.getId());
        List<GetProfilePortfolioDto> portfolios = getProfilePortfolios(user.getId(), encryptedId);
        List<SkillDto> skills = userSkillService.getUserSKill(user.getId());
        return profileMapper.toGetProfileResponseDto(user, profileImgUrl, links, awards,
                portfolios, skills);
    }

    private List<GetProfileUserLinkDto> getProfileLinks(Long userId) {
        List<UserLink> userLinks = userLinkService.getUserLink(userId);
        return userLinks.stream().map(userLinkMapper::toGetProfileUserLinkDto).toList();
    }

    private List<GetProfileAwardDto> getProfileAwards(Long userId) {
        List<Award> awards = awardService.getAward(userId);
        return awards.stream().map(awardMapper::toGetProfileAwardDto).toList();
    }

    private List<GetProfilePortfolioDto> getProfilePortfolios(Long userId, String encryptedId) {
        List<Portfolio> portfolios = portfolioService.getMyPinPortfolio(userId);

        return portfolios.stream().map((portfolio) -> {
            String imageUrl = s3Service.createPreSignedGetUrl(
                    S3FileName.getPortfolioUrl(encryptedId),
                    portfolio.getMainImageFileName());
            return portfolioMapper.toGetProfilePortfolioDto(portfolio, imageUrl);
        }).toList();
    }
}
