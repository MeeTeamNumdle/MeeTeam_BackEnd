package synk.meeteam.domain.recruitment.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.meeteam.dto.request.CreateMeeteamRequestDto;
import synk.meeteam.domain.meeteam.entity.Meeteam;
import synk.meeteam.domain.meeteam.service.MeeteamService;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;
import synk.meeteam.domain.recruitment.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.dto.request.CreateRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.dto.response.CreateRecruitmentResponseDto;
import synk.meeteam.domain.recruitment.entity.Recruitment;
import synk.meeteam.domain.recruitment.service.RecruitmentService;
import synk.meeteam.domain.recruitment_member.service.RecruitmentMemberService;
import synk.meeteam.domain.role.service.RoleService;
import synk.meeteam.domain.spec.service.SpecService;
import synk.meeteam.domain.tag.dto.request.CreateCourseTagRequestDto;
import synk.meeteam.domain.tag.entity.Tag;
import synk.meeteam.domain.tag.service.TagService;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentFacade {
    private static final String TEMP_TITLE = "의 밋팀";

    private final TagService tagService;
    private final SpecService specService;
    private final RecruitmentService recruitmentService;
    private final MeeteamService meeteamService;
    private final UserService userService;
    private final RoleService roleService;
    private final RecruitmentMemberService recruitmentMemberService;


    @Transactional
    public CreateRecruitmentResponseDto createRecruitment(final CreateRecruitmentRequestDto requestDto,
                                                          final User user) {
        // 0-1. tag 생성 로직 필요
        List<Tag> meeteamTags = tagService.crateTags(requestDto.meeteamTags(),
                CreateCourseTagRequestDto.of(requestDto.courseRequestDto().isCourse(),
                        requestDto.courseRequestDto().courseTagName()));

        // 1. 밋팀 생성 - 범위, 분야, 유형, 진행방식, 기간, 공개여부, 수업여부, 태그, 커버이미지
        String tempTitle = userService.getUserName(user) + TEMP_TITLE;

        Meeteam createdMeeteam = meeteamService.createMeeteam(
                CreateMeeteamRequestDto.of(user.getId(), tempTitle, null, true,
                        requestDto.courseRequestDto().isCourse(), requestDto.meeteamScope(), requestDto.meeteamCategory(),
                        requestDto.meeteamProceed(), requestDto.proceedingStart(), requestDto.proceedingEnd(),
                        requestDto.isPublic(), requestDto.field(), requestDto.coverImageUrl(),
                        meeteamTags), user);


        // 2. 구인글 생성 - 구인글 제목, 구인글 내용??, deadline
        Recruitment createdRecruitment = recruitmentService.createRecruitment(
                CreateRecruitmentPostRequestDto.of(createdMeeteam, requestDto.title(), requestDto.content(),
                        requestDto.deadline()));

        // 3. Role 있는지 확인, 없으면 생성
        List<CreateRecruitmentRoleAndSpecDto> recruitmentRoles = roleService.createRecruitmentRole(
                requestDto.recruitmentRoles());

        // 3-1. spec 생성 로직 필요
        List<CreateRecruitmentRoleAndSpecDto> requiredMemberRoleAndSpecs = specService.crateRecruitmentMemberSpecs(recruitmentRoles);


        // 4. RecruitmentMember 여러개 생성
        // 4-1. 해당 엔티티 만든 후에, 해당 id로 RecruitmentMemberSpec 여러개 생성
        recruitmentMemberService.createRecruitmentMembers(createdRecruitment, requiredMemberRoleAndSpecs);

        return CreateRecruitmentResponseDto.of(user.getPlatformId());
    }

}
