package synk.meeteam.domain.user.award.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.award.entity.AwardMapper;
import synk.meeteam.domain.user.award.repository.AwardRepository;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    @Transactional
    public List<Award> changeAward(Long id, List<UpdateAwardDto> awardDtos) {
        //기존 수상내역 삭제
        awardRepository.deleteAllByCreatedBy(id);
        //수상내역 생성
        List<Award> awards = awardDtos.stream().map(awardMapper::toAward).toList();
        //수상내역 저장
        return awardRepository.saveAll(awards);
    }

    @Override
    public List<Award> getAward(Long userId) {
        return awardRepository.findAllByCreatedBy(userId);
    }
}
