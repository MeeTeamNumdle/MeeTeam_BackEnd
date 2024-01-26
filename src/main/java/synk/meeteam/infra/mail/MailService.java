package synk.meeteam.infra.mail;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_SERVICE;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.auth.dto.request.SignUpUserRequestDto;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.user.entity.UserVO;
import synk.meeteam.infra.redis.repository.RedisUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisUserRepository redisUserRepository;

    public void sendMail(SignUpUserRequestDto requestDTO, String platformId) {
        String newEmailCode = UUID.randomUUID().toString();

        UserVO userVO = redisUserRepository.findByPlatformIdOrElseThrowException(platformId);
        userVO.updateEmailCode(newEmailCode);
        redisUserRepository.save(userVO);

        String receiverMail = requestDTO.email();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(RecipientType.TO, receiverMail);// 보내는 대상
            message.setSubject("Meeteam 회원가입 이메일 인증");// 제목

            String body = "<div>"
                    + "<h1> 안녕하세요. Meeteam 입니다</h1>"
                    + "<br>"
                    + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                    + "<a href='http://localhost:8080/auth/verify?emailCode=" + newEmailCode + "'>인증 링크</a>"
                    + "</div>";

            message.setText(body, "utf-8", "html");// 내용, charset 타입, subtype
            // 보내는 사람의 이메일 주소, 보내는 사람 이름
            message.setFrom(new InternetAddress("thdalsrb79@naver.com", "Meeteam"));// 보내는 사람
            mailSender.send(message); // 메일 전송
        } catch (Exception e) {
            log.info("{}", e);
            throw new AuthException(INVALID_MAIL_SERVICE);
        }
    }

    public UserVO verify(String emailCode) {
        return redisUserRepository.findByEmailCodeOrElseThrowException(emailCode);
    }
}
