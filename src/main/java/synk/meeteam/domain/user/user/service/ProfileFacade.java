package synk.meeteam.domain.user.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.user.award.service.AwardService;
import synk.meeteam.domain.user.user.dto.UpdateProfileCommandMapper;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.service.UserLinkService;
import synk.meeteam.domain.user.user_skill.service.UserSkillService;

@Service
@RequiredArgsConstructor
public class ProfileFacade {

    private final UserService userService;
    private final UserLinkService userLinkService;
    private final UserSkillService userSkillService;
    private final PortfolioService portfolioService;
    private final AwardService awardService;

    private final UpdateProfileCommandMapper updateProfileCommandMapper;


    @Transactional
    public void editProfile(User user, UpdateProfileRequestDto profileDto) {
        //유저 닉네임 덮어쓰기
        userService.changeOrKeepNickname(user, profileDto.nickname());

        //프로필 유저 정보 업데이트
        userService.changeUserInfo(user, updateProfileCommandMapper.toUpdateProfileCommand(profileDto));

        //유저 링크 덮어쓰기
        userLinkService.changeUserLinks(user, profileDto.links());

        //유저 스킬 덮어쓰기
        userSkillService.changeUserSkillsByIds(user, profileDto.skills());

        //수상 활동 내역 덮어쓰기
        awardService.changeAward(user, profileDto.awards());

        //핀 설정
        portfolioService.changePinPortfoliosByIds(user, profileDto.portfolios());
    }
}
