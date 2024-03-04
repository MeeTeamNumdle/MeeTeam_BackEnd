package synk.meeteam.domain.common.skill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.repository.SKillRepository;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SKillRepository sKillRepository;

    public List<SkillDto> searchByKeyword(String keyword, long limit) {
        return sKillRepository.findAllByKeywordAndTopLimit(keyword, limit);
    }
}
