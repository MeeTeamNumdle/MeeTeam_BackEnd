package synk.meeteam.domain.recruitment.recruitment_post.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SimpleRecruitmentPostDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.PageInfo;
import synk.meeteam.global.dto.PaginationDto;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class PostManagementController implements PostManagementApi {

    @GetMapping("/bookmark")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getBookmarkPost(@AuthUser User user,
                                                                   @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return new PaginationDto<SimpleRecruitmentPostDto>(
                List.of(new SimpleRecruitmentPostDto(
                        1L,
                        "같이할사람 구합니다.",
                        "프로젝트",
                        "goder",
                        "https://img.png",
                        "2021-12-15",
                        "교외",
                        true
                )),
                new PageInfo(1, 24, 1L, 1)
        );
    }

    @GetMapping("/applied")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getAppliedPost(@AuthUser User user,
                                                                  @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return new PaginationDto<SimpleRecruitmentPostDto>(
                List.of(new SimpleRecruitmentPostDto(
                        1L,
                        "같이할사람 구합니다.",
                        "프로젝트",
                        "goder",
                        "https://img.png",
                        "2021-12-15",
                        "교외",
                        true
                )),
                new PageInfo(1, 24, 1L, 1)
        );
    }

    @GetMapping("/myPost")
    @Override
    public PaginationDto<SimpleRecruitmentPostDto> getMyPost(@AuthUser User user,
                                                             @RequestParam(value = "is-closed", required = false) Boolean isClosed) {
        return new PaginationDto<SimpleRecruitmentPostDto>(
                List.of(new SimpleRecruitmentPostDto(
                        1L,
                        "같이할사람 구합니다.",
                        "프로젝트",
                        "goder",
                        "https://img.png",
                        "2021-12-15",
                        "교외",
                        true
                )),
                new PageInfo(1, 24, 1L, 1)
        );
    }
}
