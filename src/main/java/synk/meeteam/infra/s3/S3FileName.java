package synk.meeteam.infra.s3;

public class S3FileName {
    public static final String USER = "user/";
    public static final String PORTFOLIO = "portfolio/";

    public static String getPortfolioPath(String encryptedUserId) {
        return PORTFOLIO + encryptedUserId + "/";
    }

}
