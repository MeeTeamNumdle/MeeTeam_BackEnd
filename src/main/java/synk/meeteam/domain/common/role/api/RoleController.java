package synk.meeteam.domain.common.role.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.role.dto.RoleDto;
import synk.meeteam.domain.common.role.service.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController implements RoleApi {

    private final RoleService roleService;

    @GetMapping("/search")
    @Override
    public ResponseEntity<List<RoleDto>> searchRole(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {
        List<RoleDto> roles = roleService.searchByKeyword(keyword, limit);

        return ResponseEntity.ok(roles);
    }
}
