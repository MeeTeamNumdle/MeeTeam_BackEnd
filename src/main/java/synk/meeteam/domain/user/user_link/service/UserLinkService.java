package synk.meeteam.domain.user.user_link.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;
import synk.meeteam.domain.user.user_link.entity.UserLink;
import synk.meeteam.domain.user.user_link.entity.UserLinkMapper;
import synk.meeteam.domain.user.user_link.repository.UserLinkRepository;

@Service
@RequiredArgsConstructor
public class UserLinkService {

    private final UserLinkRepository userLinkRepository;

    private final UserLinkMapper userLinkMapper;

    @Transactional
    public List<UserLink> changeUserLinks(Long id, List<UpdateUserLinkDto> userLinkDtos) {
        //유저 링크 리스트 모두 삭제
        userLinkRepository.deleteAllByCreatedBy(id);
        //유저 링크 리스트로 변환
        List<UserLink> userLinks = userLinkDtos.stream().map(userLinkMapper::toUserLink).toList();
        //유저 링크 저장
        return userLinkRepository.saveAll(userLinks);
    }


}