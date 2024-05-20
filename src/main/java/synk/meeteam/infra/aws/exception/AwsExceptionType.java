package synk.meeteam.infra.aws.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum AwsExceptionType implements ExceptionType {
    FAIL_GET_URL(HttpStatus.BAD_REQUEST, "URL 요청에 실패하였습니다."),
    CAN_NOT_FOUND_KEY(HttpStatus.NOT_FOUND, "키를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
