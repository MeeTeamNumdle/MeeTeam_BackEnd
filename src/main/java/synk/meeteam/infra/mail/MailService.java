package synk.meeteam.infra.mail;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_SERVICE;
import static synk.meeteam.infra.mail.MailText.CHAR_SET;
import static synk.meeteam.infra.mail.MailText.FRONT_DOMAIN;
import static synk.meeteam.infra.mail.MailText.MAIL_CONTENT_POSTFIX;
import static synk.meeteam.infra.mail.MailText.MAIL_CONTENT_PREFIX;
import static synk.meeteam.infra.mail.MailText.MAIL_TITLE;
import static synk.meeteam.infra.mail.MailText.SENDER;
import static synk.meeteam.infra.mail.MailText.SENDER_ADDRESS;
import static synk.meeteam.infra.mail.MailText.SUB_TYPE;

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
import synk.meeteam.domain.user.user.entity.UserVO;
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
            message.setSubject(MAIL_TITLE);// 제목

            String body = createMailContent(newEmailCode);

            message.setText(body, CHAR_SET, SUB_TYPE);// 내용, charset 타입, subtype
            // 보내는 사람의 이메일 주소, 보내는 사람 이름
            message.setFrom(new InternetAddress(SENDER_ADDRESS, SENDER));// 보내는 사람
            mailSender.send(message); // 메일 전송
        } catch (Exception e) {
            log.info("{}", e);
            throw new AuthException(INVALID_MAIL_SERVICE);
        }
    }

    private static String createMailContent(String emailCode){
        return MAIL_CONTENT_PREFIX + FRONT_DOMAIN + emailCode + MAIL_CONTENT_POSTFIX;
    }

    public UserVO verify(String emailCode) {
        return redisUserRepository.findByEmailCodeOrElseThrowException(emailCode);
    }
}
