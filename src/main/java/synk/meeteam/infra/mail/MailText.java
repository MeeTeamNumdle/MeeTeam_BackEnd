package synk.meeteam.infra.mail;

public class MailText {
    public static final String MAIL_TITLE = "Meeteam 회원가입 이메일 인증";
    public static final String MAIL_CONTENT_PREFIX = "<div>"
            + "<h1> 안녕하세요. Meeteam 입니다</h1>"
            + "<br>"
            + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
            + "<a href='http://localhost:8080/auth/verify?emailCode=";
    public static final String MAIL_CONTENT_POSTFIX = "'>인증 링크</a>"
            + "</div>";

    public static final String SENDER_ADDRESS = "thdalsrb79@naver.com";
    public static final String SENDER = "Meeteam";
    public static final String CHAR_SET = "utf-8";
    public static final String SUB_TYPE = "html";



}
