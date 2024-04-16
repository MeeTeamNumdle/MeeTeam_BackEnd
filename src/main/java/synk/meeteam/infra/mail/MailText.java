package synk.meeteam.infra.mail;

public class MailText {

    // 공통 설정
    public static final String SENDER_ADDRESS = "thdalsrb79@naver.com";
    public static final String SENDER = "Meeteam";
    public static final String CHAR_SET = "utf-8";
    public static final String SUB_TYPE = "html";
    public static final String LOGO_DIR = "meeteam/";
    public static final String LOGO_FILE = "meeteam-logo.svg";


    // 이메일 인증 관련
    public static final String MAIL_TITLE_SIGNUP = "Meeteam 회원가입 이메일 인증";
    public static final String MAIL_VERIFY_TEMPLATE = "UniversityAuthMail";
    public static final String FRONT_VERIFY_DOMAIN = "https://www.meeteam.co.kr/signup/nickname?emailcode=";


    // 구인글 승인 관련
    public static final String MAIL_TITLE_APPROVE = "구인 신청 승인";
    public static final String MAIL_APPROVE_TEMPLATE = "ApproveMail";

}
