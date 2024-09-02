package synk.meeteam.domain.auth.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthExceptionType implements ExceptionType {

    /**
     * 400 Bad Request
     */
    INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼 인가코드입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_MAIL_SERVICE(HttpStatus.BAD_REQUEST, "메일 서비스를 이용할 수 없는 형식입니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "올바르지 않는 요청입니다."),
    ALREADY_REGISTER(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    INVALID_VERIFY_MAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 코드 입니다."),
    INVALID_MAIL_REGEX(HttpStatus.BAD_REQUEST, "학교 도메인과 유저의 도메인이 다릅니다."),



    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED_MEMBER_LOGIN(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    UNAUTHORIZED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 액세스 토큰입니다."),

    /**
     * 404 Not Found
     */

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유효한 유저를 찾지 못했습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "유효한 리프레시 토큰을 찾지 못했습니다.");

    private final HttpStatus status;
    private final String message;


    @Override
    public HttpStatus httpStatus() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
