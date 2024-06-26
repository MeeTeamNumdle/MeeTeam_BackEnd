package synk.meeteam.domain.recruitment.recruitment_post.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SimpleRecruitmentPostDto;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;

@Mapper(componentModel = "spring")
public interface SimpleRecruitmentPostMapper {
    @Mapping(target = "category", expression = "java(recruitmentPostVo.getCategory().getName())")
    @Mapping(target = "scope", expression = "java(recruitmentPostVo.getScope().getName())")
    @Mapping(target = "writerProfileImg", source = "writerProfileImg")
    @Mapping(target = "writerId", source = "writerId")
    SimpleRecruitmentPostDto toSimpleRecruitmentPostDto(RecruitmentPostVo recruitmentPostVo, String writerId,
                                                        String writerProfileImg);
}
