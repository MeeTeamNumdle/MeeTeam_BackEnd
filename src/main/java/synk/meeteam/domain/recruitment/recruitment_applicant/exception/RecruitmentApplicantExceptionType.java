package synk.meeteam.domain.recruitment.recruitment_applicant.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum RecruitmentApplicantExceptionType implements ExceptionType {
    SS_600(HttpStatus.BAD_REQUEST, "SS-600"),
    SS_602(HttpStatus.BAD_REQUEST, "SS-602"),
    INVALID_USER(HttpStatus.BAD_REQUEST, "신청자 정보에 접근할 수 없습니다."),
    ALREADY_PROCESSED_APPLICANT(HttpStatus.BAD_REQUEST, "이미 신청/거절된 신청자입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 신청자 처리 요청입니다.");


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
