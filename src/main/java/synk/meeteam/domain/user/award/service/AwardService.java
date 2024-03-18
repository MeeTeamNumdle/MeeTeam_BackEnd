package synk.meeteam.domain.user.award.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.award.dto.AwardMapper;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.award.repository.AwardRepository;
import synk.meeteam.domain.user.user.entity.User;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    @Transactional
    public void changeAward(User user, List<UpdateAwardDto> awardDtos) {
        //기존 수상내역 삭제
        awardRepository.deleteAllByUser(user);
        //수상내역 생성
        List<Award> awards = awardDtos.stream().map(awardDto -> awardMapper.toAward(user, awardDto)).toList();
        //수상내역 저장
        awardRepository.saveAll(awards);
    }
}
