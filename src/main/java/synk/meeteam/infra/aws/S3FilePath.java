package synk.meeteam.infra.aws;

public class S3FilePath {
    public static final String USER = "user/";
    public static final String PORTFOLIO = "portfolio/";

    public static String getPortfolioPath(String encryptedUserId) {
        return PORTFOLIO + encryptedUserId + "/";
    }

    public static String getZipFileFullName(String filename) {
        return filename + ".zip";
    }

    public static String getFileFullName(String filename, String extension) {
        return filename + "." + extension;
    }

}
