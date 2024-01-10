package synk.meeteam.domain.meeteam.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.meeteam_tag.entity.MeeteamTag;
import synk.meeteam.domain.member.entity.Member;
import synk.meeteam.domain.recruitment.entity.Recruitment;
import synk.meeteam.domain.meeteam_link.entity.MeeteamLink;
import synk.meeteam.domain.user.entity.User;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeteam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeteam_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User leader;

    @OneToMany(mappedBy = "meeteam")
    private List<Member> members = new ArrayList<>();

    private Long likeCount;
    private String name;

    //소개
    //상태

    //기간 시작일
    private LocalDateTime proceedingStart;

    //기간 종료일
    private LocalDateTime proceedingEnd;

    //공개 여부
    private Boolean isPublic;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "meeteam")
    private List<MeeteamTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "meeteam")
    private List<MeeteamLink> links = new ArrayList<>();

    //구인 정보
    @OneToOne(mappedBy = "meeteam")
    private Recruitment recruitment;
}
