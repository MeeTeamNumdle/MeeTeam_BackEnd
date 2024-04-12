package synk.meeteam.domain.common.skill.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.service.SkillService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController implements SkillApi {

    private final SkillService skillService;

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<SkillDto>> searchSkill(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        return ResponseEntity.ok().body(skillService.searchByKeyword(keyword, limit));
    }
}
