package synk.meeteam.domain.recruitment.recruitment_tag.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_id")
    private RecruitmentPost recruitmentPost;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public RecruitmentTag(RecruitmentPost recruitmentPost, Tag tag) {
        this.recruitmentPost = recruitmentPost;
        this.tag = tag;
    }

    public void updateTag(Tag tag) {
        this.tag = tag;
    }
}
