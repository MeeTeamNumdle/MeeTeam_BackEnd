package synk.meeteam.domain.recruitment.recruitment_post;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import synk.meeteam.domain.recruitment.recruitment_post.dto.SearchCondition;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;

@Sql({"classpath:test.sql"})
@ActiveProfiles("test")
@DataJpaTest
public class RecruitmentPostCustomRepositoryTest {

    @Autowired
    RecruitmentPostRepository recruitmentPostRepository;

    /*
     분야검색 생략
     정렬은 작성일 역순
     로그인 한 경우
     - 유저의 북마크 여부 체크
     로그인 안한 경우
     - 북마크는 false 고정
     */

    /*
     * 검색조건 없는 경우
     */
    @Test
    void 검색_성공_로그인O검색조건X() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                null,
                new User(1L)
        );
        testSearch(result, 6, 2, "프로젝트입니다1", "프로젝트입니다2", "스터디입니다1");
    }

    /**
     * 교내 정보 없어야함
     */
    @Test
    void 검색_성공_로그인X검색조건X() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                null,
                User.builder().build()
        );
        testSearch(result, 3, 1, "프로젝트입니다2", "스터디입니다2", "공모전입니다2");
    }


    /*
     * 범위 검색
     */
    @Test
    void 검색_성공_로그인O범위검색() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.ON_CAMPUS, null, null, null, null),
                null,
                new User(1L)
        );
        testSearch(result, 3, 1, "프로젝트입니다1", "스터디입니다1", "공모전입니다1");
    }

    /*
    유형 검색
     */
    @Test
    void 검색_성공_로그인X유형검색() {
        Page<RecruitmentPostVo> projectResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.PROJECT, null, null, null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> studyResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.STUDY, null, null, null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> contestResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.CONTEST, null, null, null),
                null,
                User.builder().build()
        );

        testSearch(projectResult, 1, 1, "프로젝트입니다2");
        testSearch(studyResult, 1, 1, "스터디입니다2");
        testSearch(contestResult, 1, 1, "공모전입니다2");
    }

    @Test
    void 검색_성공_로그인O유형검색() {
        Page<RecruitmentPostVo> projectResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.PROJECT, null, null, null),
                null,
                new User(1L)

        );

        Page<RecruitmentPostVo> studyResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.STUDY, null, null, null),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> contestResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.CONTEST, null, null, null),
                null,
                new User(1L)
        );

        testSearch(projectResult, 2, 1, "프로젝트입니다1", "프로젝트입니다2");
        testSearch(studyResult, 2, 1, "스터디입니다1", "스터디입니다2");
        testSearch(contestResult, 2, 1, "공모전입니다1", "공모전입니다2");
    }

    /*
    역할 검색
     */
    @Test
    void 검색_성공_로그인X역할검색() {
        Page<RecruitmentPostVo> role1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(1L)),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> role2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(2L)),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> role12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(1L, 2L)),
                null,
                User.builder().build()
        );

        testSearch(role1Result, 0, 0);
        testSearch(role2Result, 1, 1, "스터디입니다2");
        testSearch(role12Result, 1, 1, "스터디입니다2");
    }

    @Test
    void 검색_성공_로그인O역할검색() {
        Page<RecruitmentPostVo> role1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(1L)),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> role2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(2L)),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> role12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(1L, 2L)),
                null,
                new User(1L)
        );

        testSearch(role1Result, 1, 1, "스터디입니다1");
        testSearch(role2Result, 1, 1, "스터디입니다2");
        testSearch(role12Result, 2, 1, "스터디입니다1", "스터디입니다2");
    }

    /*
    스킬 검색
     */
    @Test
    void 검색_성공_로그인X스킬검색() {
        Page<RecruitmentPostVo> skill1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 2L), null, null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> skill2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(3L, 4L), null, null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> skill12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 3L), null, null),
                null,
                User.builder().build()
        );

        testSearch(skill1Result, 0, 0);
        testSearch(skill2Result, 1, 1, "스터디입니다2");
        testSearch(skill12Result, 1, 1, "스터디입니다2");
    }

    @Test
    void 검색_성공_로그인O스킬검색() {
        Page<RecruitmentPostVo> skill1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 2L), null, null),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> skill2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(3L, 4L), null, null),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> skill12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 3L), null, null),
                null,
                new User(1L)
        );

        testSearch(skill1Result, 1, 1, "스터디입니다1");
        testSearch(skill2Result, 1, 1, "스터디입니다2");
        testSearch(skill12Result, 2, 1, "스터디입니다1", "스터디입니다2");
    }

    /*
    태그 검색
     */
    @Test
    void 검색_성공_로그인X태그검색() {
        Page<RecruitmentPostVo> tag1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L), null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> tag2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(3L), null),
                null,
                User.builder().build()
        );

        Page<RecruitmentPostVo> tag12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L, 3L), null),
                null,
                User.builder().build()
        );

        testSearch(tag1Result, 0, 0);
        testSearch(tag2Result, 1, 1, "공모전입니다2");
        testSearch(tag12Result, 1, 1, "공모전입니다2");
    }

    @Test
    void 검색_성공_로그인O태그검색() {
        Page<RecruitmentPostVo> tag1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L), null),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> tag2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(3L), null),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> tag12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L, 3L), null),
                null,
                new User(1L)
        );

        testSearch(tag1Result, 1, 1, "공모전입니다1");
        testSearch(tag2Result, 1, 1, "공모전입니다2");
        testSearch(tag12Result, 2, 1, "공모전입니다1", "공모전입니다2");
    }

    /*
    북마크 검색
     */
    @Test
    void 검색_성공_로그인X북마크테스트() {
        Page<RecruitmentPostVo> role1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(1L)),
                null,
                new User(1L)
        );

        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                null,
                User.builder().build()
        );

        assertThat(result).extracting("isBookmarked")
                .containsExactly(false, false, false);
    }

    @Test
    void 검색_성공_로그인O북마크테스트() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                null,
                new User(1L)
        );

        assertThat(result).extracting("isBookmarked")
                .containsExactly(true, true, false);
    }

    /*
    키워드 검색
     */
    @Test
    void 검색_성공_로그인X키워드검색() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                "프로젝트",
                User.builder().build()
        );

        testSearch(result, 1, 1, "프로젝트입니다2");
    }

    @Test
    void 검색_성공_로그인O키워드검색() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                "프로젝트",
                new User(1L)
        );

        testSearch(result, 2, 1, "프로젝트입니다1", "프로젝트입니다2");
    }

    private void testSearch(Page<RecruitmentPostVo> result, int totalElements, int totalPages) {
        assertThat(result.getTotalElements()).isEqualTo(totalElements);
        assertThat(result.getTotalPages()).isEqualTo(totalPages);
        assertThat(result.getContent().size()).isEqualTo(0);
    }

    private void testSearch(Page<RecruitmentPostVo> result, int totalElements, int totalPages,
                            String... titles) {
        assertThat(result.getTotalElements()).isEqualTo(totalElements);
        assertThat(result.getTotalPages()).isEqualTo(totalPages);
        assertThat(result.getContent())
                .extracting("title")
                .containsExactly(titles);
    }
}
