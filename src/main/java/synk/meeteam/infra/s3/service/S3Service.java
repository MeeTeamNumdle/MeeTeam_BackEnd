package synk.meeteam.infra.s3.service;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import synk.meeteam.global.entity.ServiceType;
import synk.meeteam.infra.s3.config.S3Config;
import synk.meeteam.infra.s3.service.vo.PreSignedUrlVO;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private static final Long PRE_SIGNED_URL_EXPIRE_MINUTE = 1L;

    private final S3Config s3Config;
    @Value("${aws-property.s3-bucket-name}")
    private String bucketName;

    public String createPreSignedGetUrl(final String path, final String fileName) {
        if (fileName == null) {
            return null;
        }
        String key = path + fileName;

        S3Presigner preSigner = s3Config.getS3Presigner();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = preSigner.presignGetObject(presignRequest);
        log.info("Presigned URL: [{}]", presignedGetObjectRequest.url().toString());
        log.info("HTTP method: [{}]", presignedGetObjectRequest.httpRequest().method());

        return presignedGetObjectRequest.url().toExternalForm();
    }

    public PreSignedUrlVO getUploadPreSignedUrl(final String prefix, final String fileName, final String extension,
                                                ServiceType serviceType) {
        String actualFileName = fileName;

        if (actualFileName == null) {
            actualFileName = generateZipFileName();
        }
        actualFileName = actualFileName + "." + extension;

        String key = prefix + actualFileName;

        S3Presigner preSigner = s3Config.getS3Presigner();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectPresignRequest preSignedUrlRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest = preSigner.presignPutObject(preSignedUrlRequest);
        log.info("Presigned URL: [{}]", presignedPutObjectRequest.url().toString());
        log.info("HTTP method: [{}]", presignedPutObjectRequest.httpRequest().method());

        return PreSignedUrlVO.of(serviceType, actualFileName, presignedPutObjectRequest.url().toExternalForm());
    }

    private String generateZipFileName() {
        return UUID.randomUUID().toString();
    }

}
