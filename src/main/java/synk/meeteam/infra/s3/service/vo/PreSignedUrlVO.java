package synk.meeteam.infra.s3.service.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import synk.meeteam.global.entity.ServiceType;

@Data
@AllArgsConstructor
public class PreSignedUrlVO {

    private ServiceType serviceType;
    private String fileName;
    private String url;

    public static PreSignedUrlVO of(ServiceType serviceType, String fileName, String url) {
        return new PreSignedUrlVO(serviceType, fileName, url);
    }
}
