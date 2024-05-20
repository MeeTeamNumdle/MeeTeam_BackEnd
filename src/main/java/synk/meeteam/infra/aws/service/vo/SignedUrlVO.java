package synk.meeteam.infra.aws.service.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import synk.meeteam.global.entity.ServiceType;

@Data
@AllArgsConstructor
public class SignedUrlVO {

    private ServiceType serviceType;
    private String fileName;
    private String url;

    public static SignedUrlVO of(ServiceType serviceType, String fileName, String url) {
        return new SignedUrlVO(serviceType, fileName, url);
    }
}
