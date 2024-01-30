package synk.meeteam.domain.meeteam.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.meeteam.dto.request.CreateMeeteamRequestDto;
import synk.meeteam.domain.meeteam.entity.Meeteam;
import synk.meeteam.domain.meeteam.entity.MeeteamStatus;
import synk.meeteam.domain.meeteam.repository.MeeteamRepository;
import synk.meeteam.domain.meeteam_tag.entity.MeeteamTag;
import synk.meeteam.domain.meeteam_tag.repository.MeeteamTagRepository;
import synk.meeteam.domain.tag.entity.Tag;
import synk.meeteam.domain.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeeteamService {
    private final MeeteamRepository meeteamRepository;
    private final MeeteamTagRepository meeteamTagRepository;

    @Transactional
    public Meeteam createMeeteam(final CreateMeeteamRequestDto requestDto, final User user) {
        Meeteam newMeeteam = Meeteam.builder()
                .leader(user)
                .name(requestDto.name())
                .likeCount(0L)
                .introduction(requestDto.introduction())
                .meeteamStatus(MeeteamStatus.PRODUCING)
                .isRecruiting(requestDto.isRecruiting())
                .isCourse(requestDto.isCourse())
                .meeteamScope(requestDto.meeteamScope())
                .meeteamCategory(requestDto.meeteamCategory())
                .meeteamProceed(requestDto.meeteamProceed())
                .proceedingStart(requestDto.proceedingStart())
                .proceedingEnd(requestDto.proceedingEnd())
                .isPublic(requestDto.isPublic())
                .field(requestDto.field())
                .coverImageUrl(requestDto.coverImgUrl())
                .build();

        meeteamRepository.saveAndFlush(newMeeteam);

        createMeeteamTags(requestDto.meeteamTags(), newMeeteam);

        return newMeeteam;
    }


    private void createMeeteamTags(List<Tag> meeteamTags, Meeteam meeteam){
        for(Tag curTag : meeteamTags){
            MeeteamTag meeteamTag = MeeteamTag.builder()
                    .meeteam(meeteam)
                    .tag(curTag)
                    .build();
            meeteamTagRepository.save(meeteamTag);
        }
    }
}
