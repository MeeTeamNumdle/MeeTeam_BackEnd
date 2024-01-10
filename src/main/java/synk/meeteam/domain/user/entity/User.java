package synk.meeteam.domain.user.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.meeteam_invite.entity.MeeteamInvite;
import synk.meeteam.domain.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.follow.entity.Follow;
import synk.meeteam.domain.timetable_block.entity.TimetableBlock;
import synk.meeteam.domain.user_interest.entity.UserInterest;
import synk.meeteam.domain.user_interest_tag.entity.UserInterestTag;
import synk.meeteam.domain.user_link.entity.UserLink;
import synk.meeteam.domain.user_spec.entity.UserSpec;
import synk.meeteam.domain.meeteam.entity.Meeteam;
import synk.meeteam.domain.university.entity.University;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String password;

    private String phoneNumber;

    private Double gpa;

    private Integer admissionYear;

    private String pictureUrl;

    //유저 밋팀
    @OneToMany(mappedBy = "user")
    private List<Meeteam> meeteams = new ArrayList<>();

    //평가 점수
    private String evaluationScore;

    //학사 정보
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    //관심사
    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterests = new ArrayList<>();

    //스펙
    @OneToMany(mappedBy = "user")
    private List<UserSpec> userSpec = new ArrayList<>();

    //시간표
    @OneToMany(mappedBy = "user")
    private List<TimetableBlock> timetable = new ArrayList<>();

    //링크
    @OneToMany(mappedBy = "user")
    private List<UserLink> links = new ArrayList<>();

    //관심있는 태그
    @OneToMany(mappedBy = "user")
    private List<UserInterestTag> interestTags = new ArrayList<>();

    //팔로워
    @OneToMany(mappedBy = "toUser")
    private Set<Follow> followers = new HashSet<>();

    //팔로잉
    @OneToMany(mappedBy = "fromUser")
    private Set<Follow> followings = new HashSet<>();

    //신청한 구인 글
    @OneToMany(mappedBy = "applicant")
    private List<RecruitmentApplicant> appliedRecruitment = new ArrayList<>();

    //초대받은 밋팀
    @OneToMany(mappedBy = "user")
    private Set<MeeteamInvite> invitedMeeteams = new HashSet<>();
}
