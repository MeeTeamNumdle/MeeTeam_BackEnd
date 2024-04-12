package synk.meeteam.domain.recruitment.recruitment_tag.service;

import static synk.meeteam.domain.recruitment.recruitment_tag.exception.RecruitmentTagExceptionType.TAG_NAME_EXCEED_LIMIT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.tag.dto.TagDto;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.repository.TagRepository;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.exception.RecruitmentTagException;
import synk.meeteam.domain.recruitment.recruitment_tag.repository.RecruitmentTagRepository;

@RequiredArgsConstructor
@Service
public class RecruitmentTagService {

    private final RecruitmentTagRepository recruitmentTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public List<RecruitmentTag> createRecruitmentTags(List<RecruitmentTag> recruitmentTags) {
        return recruitmentTags.stream().map(recruitmentTag -> createRecruitmentTag(recruitmentTag))
                .toList();
    }

    @Transactional
    public RecruitmentTag createRecruitmentTag(RecruitmentTag recruitmentTag) {
        Tag foundTag = tagRepository.findByName(recruitmentTag.getTag().getName()).orElse(null);
        if (foundTag == null) {
            try {
                foundTag = tagRepository.save(recruitmentTag.getTag());
            } catch (DataIntegrityViolationException e) {  // 글자수 초과
                throw new RecruitmentTagException(TAG_NAME_EXCEED_LIMIT);
            }
        }

        recruitmentTag.updateTag(foundTag);

        return recruitmentTagRepository.save(recruitmentTag);
    }

    public List<TagDto> findByRecruitmentPostId(Long postId) {
        return tagRepository.findAllByRecruitmentId(postId);
    }

    @Transactional
    public void modifyRecruitmentTag(List<RecruitmentTag> recruitmentTags, Long postId) {
        recruitmentTagRepository.deleteAllByRecruitmentPostId(postId);
        createRecruitmentTags(recruitmentTags);
    }
}
