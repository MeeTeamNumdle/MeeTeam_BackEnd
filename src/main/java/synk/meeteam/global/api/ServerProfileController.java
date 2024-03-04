package synk.meeteam.global.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.infra.s3.service.S3Service;
import synk.meeteam.infra.s3.service.vo.PreSignedUrlVO;

@RestController
@RequiredArgsConstructor
public class ServerProfileController {

    private final Environment env;
    private final S3Service s3Service;

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok(env.getActiveProfiles()[0]);
    }

    @GetMapping("/presigned-get-test")
    public ResponseEntity<String> getPreSignedGetUrl() {
        return ResponseEntity.ok().body(s3Service.createPreSignedGetUrl("test-folder/", "test1.png"));
    }

    @GetMapping("/presigned-post-test")
    public ResponseEntity<PreSignedUrlVO> getPreSignedPutUrl() {
        return ResponseEntity.ok().body(s3Service.getUploadPreSignedUrl("test-folder/"));
    }
}
