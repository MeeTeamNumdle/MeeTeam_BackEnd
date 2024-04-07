package synk.meeteam.infra.mail;

public class MailText {
    public static final String MAIL_TITLE_SIGNUP = "Meeteam 회원가입 이메일 인증";
    public static final String MAIL_TITLE_APPROVE = "구인 신청 승인";

    public static final String MAIL_CONTENT_PREFIX_SIGNUP = "<div>"
            + "<h1> 안녕하세요. Meeteam 입니다</h1>"
            + "<br>"
            + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
            + "<a href='";

    public static final String MAIL_CONTENT_PREFIX_APPROVE = "<div>"
            + "<h1> 안녕하세요. Meeteam 입니다</h1>"
            + "<br>"
            + "<p>구인 신청 결과에 대해서 전해드리겠습니다!<p>"
            + "<a href='";
    public static final String FRONT_DOMAIN = "http://localhost:5173/signup/nickname?emailcode=";
    public static final String MAIL_CONTENT_POSTFIX_SIGNUP = "'>인증 링크</a>"
            + "</div>";

    public static final String MAIL_CONTENT_POSTFIX_APPROVE = "'>구인글 링크</a>"
            + "</div>";

    public static final String SENDER_ADDRESS = "thdalsrb79@naver.com";
    public static final String SENDER = "Meeteam";
    public static final String CHAR_SET = "utf-8";
    public static final String SUB_TYPE = "html";


}
