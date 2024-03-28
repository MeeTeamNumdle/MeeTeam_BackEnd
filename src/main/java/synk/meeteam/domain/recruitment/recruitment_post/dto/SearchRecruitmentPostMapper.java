package synk.meeteam.domain.recruitment.recruitment_post.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SearchRecruitmentPostDto;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;

@Mapper(componentModel = "spring")
public interface SearchRecruitmentPostMapper {
    @Mapping(target = "category", expression = "java(recruitmentPostVo.getCategory().getName())")
    @Mapping(target = "scope", expression = "java(recruitmentPostVo.getScope().getName())")
    SearchRecruitmentPostDto toSearchRecruitmentPostDto(RecruitmentPostVo recruitmentPostVo);
}
