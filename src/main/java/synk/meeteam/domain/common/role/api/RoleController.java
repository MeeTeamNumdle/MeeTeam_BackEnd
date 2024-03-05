package synk.meeteam.domain.common.role.api;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.role.dto.RoleDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController implements RoleApi {


    @GetMapping("/search")
    @Override
    public ResponseEntity<List<RoleDto>> searchRole(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        List<RoleDto> roles = Arrays.asList(new RoleDto(1L, "웹 개발자"), new RoleDto(2L, "서버 개발자"));
        return ResponseEntity.ok(roles);
    }
}
