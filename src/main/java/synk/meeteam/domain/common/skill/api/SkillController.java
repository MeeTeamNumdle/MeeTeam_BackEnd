package synk.meeteam.domain.common.skill.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.skill.dto.SkillDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController implements SkillApi {
    @Override
    @GetMapping("/list")
    public ResponseEntity<List<SkillDto>> getTotalSkills(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "limit", defaultValue = "5") long limit) {
        return null;
    }
}
