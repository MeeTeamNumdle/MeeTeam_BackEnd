package synk.meeteam.global.api;

import static synk.meeteam.infra.s3.S3FileName.PORTFOLIO;
import static synk.meeteam.infra.s3.S3FileName.USER;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.ServiceType;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.infra.s3.service.S3Service;
import synk.meeteam.infra.s3.service.vo.PreSignedUrlVO;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
public class ServerProfileController {

    private static final String ZIP_NAME = "zip";

    private final Environment env;
    private final S3Service s3Service;
    private final PortfolioService portfolioService;

    @Operation(summary = "프론트에서 필요없는 API 입니다.")
    @GetMapping("/profile")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok(env.getActiveProfiles()[0]);
    }

    // THUMBNAIL_PORTFOLIO

    @GetMapping("/profile/pre-signed-url")
    public ResponseEntity<PreSignedUrlVO> getPreSignedUrl(@AuthUser User user,
                                                          @RequestParam(name = "file-name") String fileName) {
        String extension = StringUtils.getFilenameExtension(fileName);

        return ResponseEntity.ok().body(s3Service.getUploadPreSignedUrl(USER, Encryption.encryptLong(user.getId()),
                extension, ServiceType.PROFILE));
    }

    @GetMapping("/portfolio/pre-signed-url")
    public ResponseEntity<List<PreSignedUrlVO>> getPreSignedUrl(@AuthUser User user,
                                                                @RequestParam(name = "file-name") String fileName,
                                                                @RequestParam(name = "portfolio", required = false) Long portfolioId
    ) {

        String extension = StringUtils.getFilenameExtension(fileName);

        String zipFileName = null;
        String thumbNailFileName = null;

        if (portfolioId != null) {
            // 수정의 경우
            Portfolio portfolio = portfolioService.getPortfolio(portfolioId, user);
            zipFileName = portfolio.getZipFileName();
            thumbNailFileName = portfolio.getMainImageFileName();
        }

        PreSignedUrlVO zipUrl = s3Service.getUploadPreSignedUrl(PORTFOLIO, zipFileName,
                ZIP_NAME, ServiceType.PORTFOLIOS);
        PreSignedUrlVO thumbNailUrl = s3Service.getUploadPreSignedUrl(PORTFOLIO, thumbNailFileName,
                extension, ServiceType.THUMBNAIL_PORTFOLIO);

        return ResponseEntity.ok().body(List.of(zipUrl, thumbNailUrl));

    }
}
