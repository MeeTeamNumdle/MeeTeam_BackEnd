package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import synk.meeteam.domain.recruitment.bookmark.service.BookmarkService;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_comment.service.RecruitmentCommentService;
import synk.meeteam.domain.recruitment.recruitment_post.dto.RecruitmentPostMapper;
import synk.meeteam.domain.recruitment.recruitment_post.dto.SearchCondition;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.ApplyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateCommentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.DeleteCommentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.SetOpenKakaoLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetCommentResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.PaginationSearchPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.service.RecruitmentTagService;
import synk.meeteam.domain.recruitment.recruitment_tag.service.vo.RecruitmentTagVO;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;
import synk.meeteam.infra.s3.S3FileName;
import synk.meeteam.infra.s3.service.S3Service;
import synk.meeteam.security.AuthUser;


@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/postings")
public class RecruitmentPostController implements RecruitmentPostApi {

    private final Long DEVELOP_ID = 1L;

    private final RecruitmentPostFacade recruitmentPostFacade;

    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentCommentService recruitmentCommentService;
    private final BookmarkService bookmarkService;
    private final RoleService roleService;
    private final FieldService fieldService;
    private final SkillService skillService;
    private final UserService userService;
    private final S3Service s3Service;

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

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GetRecruitmentPostResponseDto> getRecruitmentPost(
            @PathVariable("id") Long postId, @AuthUser User user) {
        // 단일 트랜잭션으로 하지 않아도 될듯
        // 트랜잭션으로 하지 않아도 될듯?
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);
        boolean isBookmarked = false;
        if (user != null) {
            isBookmarked = bookmarkService.isBookmarked(recruitmentPost, user);
        }

        User writer = userService.findById(recruitmentPost.getCreatedBy());
        String writerImgUrl = s3Service.createPreSignedGetUrl(S3FileName.USER, writer.getProfileImgFileName());

        List<RecruitmentRole> recruitmentRoles = recruitmentRoleService.findByRecruitmentPostId(postId);

        RecruitmentTagVO recruitmentTagVO = recruitmentTagService.findByRecruitmentPostId(postId);

        List<GetCommentResponseDto> recruitmentCommentDtos = recruitmentCommentService.getRecruitmentComments(
                recruitmentPost);

        return ResponseEntity.ok()
                .body(GetRecruitmentPostResponseDto.from(recruitmentPost, isBookmarked, recruitmentRoles, writer,
                        writerImgUrl,
                        recruitmentTagVO,
                        recruitmentCommentDtos));
    }

    @GetMapping("/{id}/apply-info")
    @Override
    public ResponseEntity<GetApplyInfoResponseDto> getApplyInfo(@PathVariable("id") Long postId,
                                                                @AuthUser User user) {
        List<AvailableRecruitmentRoleDto> availableRecruitmentRoleDtos = recruitmentRoleService.findAvailableRecruitmentRole(
                postId);

        return ResponseEntity.ok()
                .body(new GetApplyInfoResponseDto(user.getName(), user.getEvaluationScore(),
                        user.getUniversity().getName(), user.getDepartment().getName(),
                        user.getAdmissionYear(),
                        user.getUniversityEmail(), availableRecruitmentRoleDtos));
    }


    @PostMapping("/{id}/apply")
    @Override
    public ResponseEntity<Void> applyRecruitment(@PathVariable("id") Long postId,
                                                 @Valid @RequestBody ApplyRecruitmentRequestDto requestDto,
                                                 @AuthUser User user) {
        RecruitmentRole recruitmentRole = recruitmentRoleService.findAppliableRecruitmentRole(requestDto.applyRoleId());

        RecruitmentApplicant recruitmentApplicant = recruitmentPostMapper.toRecruitmentApplicantEntity(
                recruitmentRole.getRecruitmentPost(), recruitmentRole.getRole(), user, requestDto.message());

        recruitmentPostFacade.applyRecruitment(recruitmentRole, recruitmentApplicant);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/close")
    @Override
    public ResponseEntity<Void> closeRecruitment(@PathVariable("id") Long postId, @AuthUser User user) {
        recruitmentPostService.closeRecruitment(postId, user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Void> modifyRecruitmentPost(@Valid @RequestBody CreateRecruitmentPostRequestDto requestDto,
                                                      @PathVariable("id") Long postId, @AuthUser User user) {

        Field field = fieldService.findById(DEVELOP_ID);

        RecruitmentPost srcRecruitmentPost = recruitmentPostMapper.toRecruitmentEntity(requestDto, field);
        RecruitmentPost dstRecruitmentPost = recruitmentPostService.getRecruitmentPost(postId);

        List<RecruitmentRoleSkill> recruitmentRoleSkills = new ArrayList<>();
        List<RecruitmentRole> recruitmentRoles = getRecruitmentRoles(requestDto, dstRecruitmentPost,
                recruitmentRoleSkills);

        List<RecruitmentTag> recruitmentTags = getRecruitmentTags(requestDto, dstRecruitmentPost);

        recruitmentPostFacade.modifyRecruitmentPost(dstRecruitmentPost, srcRecruitmentPost, recruitmentRoles,
                recruitmentRoleSkills, recruitmentTags);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/bookmark")
    @Override
    public ResponseEntity<Void> bookmarkRecruitmentPost(@PathVariable("id") Long postId,
                                                        @AuthUser User user) {
        recruitmentPostFacade.bookmarkRecruitmentPost(postId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/bookmark")
    @Override
    public ResponseEntity<Void> cancelBookmarkRecruitmentPost(@PathVariable("id") Long postId,
                                                              @AuthUser User user) {
        recruitmentPostFacade.cancelBookmarkRecruitmentPost(postId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<PaginationSearchPostResponseDto> searchRecruitmentPost(
            @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
            @RequestParam(value = "field", required = false) Long fieldId,
            @RequestParam(value = "scope", required = false) Integer scopeOrdinal,
            @RequestParam(value = "category", required = false) Integer categoryOrdinal,
            @RequestParam(value = "skill", required = false) List<Long> skillIds,
            @RequestParam(value = "role", required = false) List<Long> roleIds,
            @RequestParam(value = "tag", required = false) List<Long> tagIds,
            @RequestParam(value = "keyword", required = false) String keyword,
            @AuthUser User user) {
        SearchCondition condition = getCondition(fieldId, scopeOrdinal, categoryOrdinal, skillIds, roleIds, tagIds);
        PaginationSearchPostResponseDto result = recruitmentPostService.searchWithPageRecruitmentPost(
                size, page, condition, keyword, user);

        return ResponseEntity.ok().body(result);
    }

    @Override
    @PutMapping("/{id}/openTalk")
    public ResponseEntity<Void> setOpenKaKaoLink(@AuthUser User user, @PathVariable("id") Long postId,
                                                 @Valid @RequestBody
                                                 SetOpenKakaoLinkRequestDto requestDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    @Override
    public ResponseEntity<Void> registerComment(@PathVariable("id") Long postId,
                                                @Valid @RequestBody CreateCommentRequestDto requestDto,
                                                @AuthUser User user) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/comment")
    @Override
    public ResponseEntity<Void> deleteComment(Long postId, DeleteCommentRequestDto requestDto, User user) {
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
        recruitmentTags.addAll(requestDto.tags().stream()
                .map(tagName -> recruitmentPostMapper.toTagEntity(tagName, TagType.MEETEAM))
                .map(tag -> recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost, tag))
                .toList());

        if (requestDto.courseTag().isCourse()) {
            Tag tagEntity = recruitmentPostMapper.toTagEntity(requestDto.courseTag().courseTagName(), TagType.COURSE);
            RecruitmentTag recruitmentTagEntity = recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost,
                    tagEntity);
            recruitmentTags.add(recruitmentTagEntity);
            recruitmentTags.add(recruitmentPostMapper.toRecruitmentTagEntity(recruitmentPost,
                    recruitmentPostMapper.toTagEntity(requestDto.courseTag().courseProfessor(), TagType.PROFESSOR)));
        }
        return recruitmentTags;
    }

    private SearchCondition getCondition(Long fieldId, Integer scopeOrdinal, Integer categoryOrdinal,
                                         List<Long> skillIds,
                                         List<Long> roleIds,
                                         List<Long> tagIds) {
        Scope scope = scopeOrdinal == null ? null : Scope.values()[scopeOrdinal - 1];
        Category category = categoryOrdinal == null ? null : Category.values()[categoryOrdinal - 1];
        return new SearchCondition(fieldId, scope, category, skillIds, tagIds, roleIds);
    }
}
