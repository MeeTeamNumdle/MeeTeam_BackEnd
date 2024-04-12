package synk.meeteam.infra.s3;

public class S3FileName {
    public static final String USER = "user/";
    public static final String PORTFOLIO = "portfolio/";

    public static String getPortfolioUrl(String encryptedUserId) {
        return PORTFOLIO + encryptedUserId + "/";
    }

    public static String getProfileImgUrl(String encryptedUserId) {
        return USER + encryptedUserId + "/";
    }

}
