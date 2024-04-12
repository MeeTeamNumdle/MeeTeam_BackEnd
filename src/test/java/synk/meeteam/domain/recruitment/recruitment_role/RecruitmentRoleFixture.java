package synk.meeteam.domain.recruitment.recruitment_role;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;

public class RecruitmentRoleFixture {

    public static RecruitmentRole createRecruitmentRoleFixture(RecruitmentPost recruitmentPost, Role role, long count) {
        return RecruitmentRole.builder()
                .role(role)
                .recruitmentPost(recruitmentPost)
                .count(count)
                .build();
    }

    public static List<RecruitmentRole> createRecruitmentRoles(RecruitmentPost recruitmentPost, Role role) {
        List<RecruitmentRole> recruitmentRoles = new ArrayList<>();
        recruitmentRoles.add(new RecruitmentRole(recruitmentPost, role, 3));
        recruitmentRoles.add(new RecruitmentRole(recruitmentPost, new Role("보안개발자"), 2));
        recruitmentRoles.add(new RecruitmentRole(recruitmentPost, new Role("풀스택개발자"), 1));

        return recruitmentRoles;
    }
}
