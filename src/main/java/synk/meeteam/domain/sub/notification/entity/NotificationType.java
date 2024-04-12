package synk.meeteam.domain.sub.notification.entity;

public enum NotificationType {
    //U = USER, M = MEETEAM
    M_NOT_APPLY, //유저1 님이 프로젝트 할사람 구함 에 백엔드 개발자를 신청하였습니다.
    M_NOT_APPROVE, //유저1 님이 Meeteam 초대를 수락하셨습니다.
    M_NOT_COMMENT, //유저1 님이 Meeteam 에 댓글을 남겼습니다.
    M_NOT_LIKE, //유저1 님이 Meeteam 을 좋아합니다.
    U_NOT_APPROVE, //신청하신 프로젝트 할사람 구함 에 멤버로 합류되었습니다.
    U_NOT_REJECT, //신청하신 프로젝트 할 사람 구함 에서 안타깝게도 거절되었습니다.
    U_NOT_INVITE, // Meeteam 의 리더 유저1 님이 백엔드 개발자로 초대하였습니다.
    U_NOT_FOLLOW //유저1님이 팔로우합니다.

}
