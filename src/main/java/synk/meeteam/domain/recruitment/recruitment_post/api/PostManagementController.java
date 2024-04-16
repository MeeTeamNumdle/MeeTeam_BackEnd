package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SimpleRecruitmentPostDto;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.PaginationDto;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class PostManagementController implements PostManagementApi {

    private final RecruitmentPostService recruitmentPostService;

    @GetMapping("/bookmark")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getBookmarkPost(@AuthUser User user,
                                                                   @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
                                                                   @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return recruitmentPostService.getBookmarkPost(size, page, user, isClosed);
    }

    @GetMapping("/applied")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getAppliedPost(@AuthUser User user,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
                                                                  @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
                                                                  @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return recruitmentPostService.getAppliedPost(size, page, user, isClosed);
    }

    @GetMapping("/my-post")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getMyPost(@AuthUser User user,
                                                             @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
                                                             @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
                                                             @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return recruitmentPostService.getMyPost(size, page, user, isClosed);
    }
}
