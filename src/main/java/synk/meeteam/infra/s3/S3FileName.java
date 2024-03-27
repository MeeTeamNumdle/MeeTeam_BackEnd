package synk.meeteam.infra.s3;

public class S3FileName {
    public static final String USER = "user/";
    public static final String PORTFOLIO = "portfolio/";

    public static String getPortfolioUrl(String userId) {
        return PORTFOLIO + userId + "/";
    }
}
