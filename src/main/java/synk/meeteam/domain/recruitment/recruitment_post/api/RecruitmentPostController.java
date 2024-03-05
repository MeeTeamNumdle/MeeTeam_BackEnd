package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.field.service.FieldService;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.service.RoleService;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.service.SkillService;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.recruitment.recruitment_comment.service.RecruitmentCommentService;
import synk.meeteam.domain.recruitment.recruitment_post.dto.RecruitmentPostMapper;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.GetApplyInfoRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.applyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyRecruitmentRoleResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetCommentResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.service.RecruitmentTagService;
import synk.meeteam.domain.recruitment.recruitment_tag.service.vo.RecruitmentTagVO;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.security.AuthUser;


@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/post")
public class RecruitmentPostController implements RecruitmentPostApi {

    private final Long DEVELOP_ID = 1L;

    private final RecruitmentPostFacade recruitmentPostFacade;

    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentCommentService recruitmentCommentService;
    private final RoleService roleService;
    private final FieldService fieldService;
    private final SkillService skillService;
    private final UserService userService;

    private final RecruitmentPostMapper recruitmentPostMapper;

    @PostMapping
    @Override
    public ResponseEntity<CreateRecruitmentPostResponseDto> createRecruitmentPost(
            @Valid @RequestBody CreateRecruitmentPostRequestDto requestDto) {

        Field field = fieldService.findById(DEVELOP_ID);

        RecruitmentPost recruitmentPost = recruitmentPostMapper.toRecruitmentEntity(requestDto, field);

        List<RecruitmentRoleSkill> recruitmentRoleSkills = new ArrayList<>();
        List<RecruitmentRole> recruitmentRoles = getRecruitmentRoles(requestDto, recruitmentPost,
                recruitmentRoleSkills);

        List<RecruitmentTag> recruitmentTags = getRecruitmentTags(requestDto, recruitmentPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(CreateRecruitmentPostResponseDto.from(
                recruitmentPostFacade.createRecruitmentPost(recruitmentPost, recruitmentRoles, recruitmentRoleSkills,
                        recruitmentTags)));
    }

    @GetMapping
    @Override
    public ResponseEntity<GetRecruitmentPostResponseDto> getRecruitmentPost(
            @Valid @RequestParam(value = "id") Long postId,
            @AuthUser User user) {
        // 단일 트랜잭션으로 하지 않아도 될듯
        // 트랜잭션으로 하지 않아도 될듯?
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);

        User writer = userService.findById(recruitmentPost.getCreatedBy());

        List<RecruitmentRole> recruitmentRoles = recruitmentRoleService.findByRecruitmentPostId(
                recruitmentPost.getId());

        RecruitmentTagVO recruitmentTagVO = recruitmentTagService.findByRecruitmentPostId(postId);

        List<GetCommentResponseDto> recruitmentCommentDtos = recruitmentCommentService.getRecruitmentComments(
                recruitmentPost);

        return ResponseEntity.ok()
                .body(GetRecruitmentPostResponseDto.from(recruitmentPost, recruitmentRoles, writer, recruitmentTagVO,
                        recruitmentCommentDtos));
    }

    @GetMapping("/apply")
    @Override
    public ResponseEntity<GetApplyInfoResponseDto> getApplyInfo(@Valid @RequestBody GetApplyInfoRequestDto requestDto,
                                                                @AuthUser User user) {
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(requestDto.postId());
        User foundUser = userService.findById(user.getId());
        List<RecruitmentRole> availableRecruitmentRole = recruitmentRoleService.findAvailableRecruitmentRole(
                recruitmentPost);
        List<GetApplyRecruitmentRoleResponseDto> availableRecruitmentRoleDtos = availableRecruitmentRole.stream()
                .map(GetApplyRecruitmentRoleResponseDto::from).toList();

        return ResponseEntity.ok()
                .body(new GetApplyInfoResponseDto(foundUser.getName(), foundUser.getEvaluationScore(),
                        foundUser.getUniversity().getName(), foundUser.getDepartment().getName(),
                        foundUser.getAdmissionYear(),
                        foundUser.getEmail(), availableRecruitmentRoleDtos));
    }


    @PostMapping("/apply")
    @Override
    public ResponseEntity<Void> applyRecruitment(applyRecruitmentRequestDto requestDto, User user) {

        return ResponseEntity.ok().build();
    }

    ////////////////  변환 로직들  ////////////////
    private List<RecruitmentRole> getRecruitmentRoles(CreateRecruitmentPostRequestDto requestDto,
                                                      RecruitmentPost recruitmentPost,
                                                      List<RecruitmentRoleSkill> recruitmentRoleSkills) {
        return requestDto.recruitmentRoles().stream().map(recruitmentRole -> {
            Role role = roleService.findById(recruitmentRole.roleId());
            RecruitmentRole recruitmentRoleEntity = recruitmentPostMapper.toRecruitmentRoleEntity(recruitmentPost, role,
                    recruitmentRole.count());

            recruitmentRole.skillIds().stream().forEach(skillId -> {
                Skill skill = skillService.findBySkillId(skillId);
                recruitmentRoleSkills.add(recruitmentPostMapper.toRecruitmentSkillEntity(recruitmentRoleEntity, skill));
            });
            return recruitmentRoleEntity;
        }).toList();
    }

    private List<RecruitmentTag> getRecruitmentTags(CreateRecruitmentPostRequestDto requestDto,
                                                    RecruitmentPost recruitmentPost) {
        List<RecruitmentTag> recruitmentTags = new ArrayList<>();
        recruitmentTags.addAll(requestDto.Tags().stream()
                .map(tagName -> recruitmentPostMapper.toTagEntity(tagName, TagType.MEETEAM))
                .map(tag -> recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost, tag))
                .toList());

        if (requestDto.courseTag().isCourse()) {
            Tag tagEntity = recruitmentPostMapper.toTagEntity(requestDto.courseTag().courseTagName(), TagType.COURSE);
            RecruitmentTag recruitmentTagEntity = recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost,
                    tagEntity);
            recruitmentTags.add(recruitmentTagEntity);
            recruitmentTags.add(recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost,
                    recruitmentPostMapper.toTagEntity(requestDto.courseTag().courseTagName(), TagType.PROFESSOR)));
        }
        return recruitmentTags;
    }
}
