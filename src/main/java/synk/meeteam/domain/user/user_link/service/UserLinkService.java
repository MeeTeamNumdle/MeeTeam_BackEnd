package synk.meeteam.domain.user.user_link.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;
import synk.meeteam.domain.user.user_link.dto.UserLinkMapper;
import synk.meeteam.domain.user.user_link.entity.UserLink;
import synk.meeteam.domain.user.user_link.repository.UserLinkRepository;

@Service
@RequiredArgsConstructor
public class UserLinkService {

    private final UserLinkRepository userLinkRepository;

    private final UserLinkMapper userLinkMapper;

    @Transactional
    public void changeUserLinks(User user, List<UpdateUserLinkDto> userLinkDtos) {
        //유저 링크 리스트 모두 삭제
        userLinkRepository.deleteAllByUser(user);
        //유저 링크 리스트로 변환
        List<UserLink> userLinks = userLinkDtos.stream().map(
                userLinkDto -> userLinkMapper.toUserLink(user, userLinkDto)).toList();
        //유저 링크 저장
        if (!userLinks.isEmpty()) {
            userLinkRepository.saveAll(userLinks);
        }
    }


}
