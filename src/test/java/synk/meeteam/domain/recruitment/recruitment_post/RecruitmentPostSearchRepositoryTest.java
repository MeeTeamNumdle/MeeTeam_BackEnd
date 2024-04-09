package synk.meeteam.domain.recruitment.recruitment_post;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.recruitment.recruitment_post.dto.SearchCondition;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;

@DataJpaTest
@ActiveProfiles("test")
@Sql({"classpath:test-search-post.sql"})
public class RecruitmentPostSearchRepositoryTest {

    @Autowired
    RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    UniversityRepository universityRepository;
    University university;
    User user;
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


    @BeforeEach
    void setup() {
        university = universityRepository.findById(1L).get();
        user = new User(1L, university);
    }

    @Test
    void 검색_성공_로그인O검색조건X() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                null,
                user
        );
        testSearch(result, 8, 3, "프로젝트입니다1", "프로젝트입니다2", "스터디입니다1");
    }

    /**
     * 교내 정보 없어야함
     */
    @Test
    void 검색_성공_로그인X검색조건X() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                SearchCondition.builder().scope(Scope.OFF_CAMPUS).build(),
                null,
                null
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
                SearchCondition.builder().fieldId(1L).scope(Scope.ON_CAMPUS).build(),
                null,
                user
        );
        testSearch(result, 5, 2, "프로젝트입니다1", "스터디입니다1", "공모전입니다1");
    }

    /*
    유형 검색
     */
    @Test
    void 검색_성공_로그인X유형검색() {
        Page<RecruitmentPostVo> projectResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, Category.PROJECT, null, null, null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> studyResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, Category.STUDY, null, null, null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> contestResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, Category.CONTEST, null, null, null, null, null),
                null,
                null
        );

        testSearch(projectResult, 1, 1, "프로젝트입니다2");
        testSearch(studyResult, 1, 1, "스터디입니다2");
        testSearch(contestResult, 1, 1, "공모전입니다2");
    }

    @Test
    void 검색_성공_로그인O유형검색() {
        Page<RecruitmentPostVo> projectResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.PROJECT, null, null, null, null, null),
                null,
                user

        );

        Page<RecruitmentPostVo> studyResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.STUDY, null, null, null, null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> contestResult = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, Category.CONTEST, null, null, null, null, null),
                null,
                user
        );

        testSearch(projectResult, 4, 2, "프로젝트입니다1", "프로젝트입니다2", "수업입니다.");
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
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, null, List.of(3L), null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> role2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, null, List.of(4L), null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> role12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, null, List.of(3L, 4L), null, null),
                null,
                null
        );

        testSearchEmpty(role1Result);
        testSearch(role2Result, 1, 1, "스터디입니다2");
        testSearch(role12Result, 1, 1, "스터디입니다2");
    }

    @Test
    void 검색_성공_로그인O역할검색() {
        Page<RecruitmentPostVo> role1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(3L), null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> role2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(4L), null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> role12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, null, List.of(3L, 4L), null, null),
                null,
                user
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
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, List.of(1L, 2L), null, null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> skill2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, List.of(3L, 4L), null, null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> skill12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, List.of(1L, 3L), null, null, null, null),
                null,
                null
        );

        testSearchEmpty(skill1Result);
        testSearch(skill2Result, 1, 1, "스터디입니다2");
        testSearch(skill12Result, 1, 1, "스터디입니다2");
    }

    @Test
    void 검색_성공_로그인O스킬검색() {
        Page<RecruitmentPostVo> skill1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 2L), null, null, null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> skill2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(3L, 4L), null, null, null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> skill12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, List.of(1L, 3L), null, null, null, null),
                null,
                user
        );

        testSearch(skill1Result, 2, 1, "스터디입니다1", "이건제목입니다!!");
        testSearch(skill2Result, 2, 1, "스터디입니다2", "이건제목입니다!!");
        testSearch(skill12Result, 3, 1, "스터디입니다1", "스터디입니다2", "이건제목입니다!!");
    }

    /*
    태그 검색
     */
    @Test
    void 검색_성공_로그인X태그검색() {
        Page<RecruitmentPostVo> tag1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, List.of(1L), null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> tag2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, List.of(3L), null, null, null),
                null,
                null
        );

        Page<RecruitmentPostVo> tag12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, Scope.OFF_CAMPUS, null, null, List.of(1L, 3L), null, null, null),
                null,
                null
        );

        testSearchEmpty(tag1Result);
        testSearch(tag2Result, 1, 1, "공모전입니다2");
        testSearch(tag12Result, 1, 1, "공모전입니다2");
    }

    @Test
    void 검색_성공_로그인O태그검색() {
        Page<RecruitmentPostVo> tag1Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L), null, null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> tag2Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(4L), null, null, null),
                null,
                user
        );

        Page<RecruitmentPostVo> tag12Result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(1L, null, null, null, List.of(1L, 3L, 4L), null, null, null),
                null,
                user
        );

        testSearch(tag1Result, 2, 1, "공모전입니다1", "이건제목입니다!!");
        testSearch(tag2Result, 1, 1, "공모전입니다2");
        testSearch(tag12Result, 3, 1, "공모전입니다1", "공모전입니다2", "이건제목입니다!!");
    }

    /*
    북마크 검색
     */
    @Test
    void 검색_성공_로그인X북마크테스트() {

        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                SearchCondition.builder().scope(Scope.OFF_CAMPUS).build(),
                null,
                null
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
                user
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
                SearchCondition.builder().scope(Scope.OFF_CAMPUS).build(),
                "프로젝트",
                null
        );

        testSearch(result, 1, 1, "프로젝트입니다2");
    }

    @Test
    void 검색_성공_로그인O키워드검색() {
        Page<RecruitmentPostVo> result = recruitmentPostRepository.findBySearchConditionAndKeyword(
                PageRequest.of(0, 3),
                new SearchCondition(),
                "프로젝트",
                user
        );

        testSearch(result, 2, 1, "프로젝트입니다1", "프로젝트입니다2");
    }

    private void testSearchEmpty(Page<RecruitmentPostVo> result) {
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);
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
