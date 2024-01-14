package synk.meeteam.infra.mail;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_SERVICE;
import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_VERIFY_MAIL;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.auth.api.dto.request.UserSignUpRequestDTO;
import synk.meeteam.domain.auth.api.dto.request.UserVerifyRequestDTO;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.infra.redis.repository.RedisTokenRepository;
import synk.meeteam.infra.redis.repository.RedisVerifyRepository;
import synk.meeteam.security.jwt.service.vo.TokenVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisVerifyRepository redisVerifyRepository;
    private final UserRepository userRepository;

    public void sendMail(UserSignUpRequestDTO requestDTO) {
        String token = UUID.randomUUID().toString();

        MailVO mailVO = MailVO.builder()
                .platformId(requestDTO.platformId())
                .platformType(requestDTO.platformType())
                .emailCode(token)
                .email(requestDTO.email())
                .build();

        redisVerifyRepository.save(mailVO);

        String receiverMail = requestDTO.email();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(RecipientType.TO, receiverMail);// 보내는 대상
            message.setSubject("Meeteam 회원가입 이메일 인증");// 제목

            String body = "<div>"
                    + "<h1> 안녕하세요. Meeteam 입니다</h1>"
                    + "<br>"
                    + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                    + "<a href='http://localhost:8080/auth/verify?emailCode=" + token + "'>인증 링크</a>"
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

    public User verify(String token) {
        MailVO mailVO = redisVerifyRepository.findByEmailCodeOrElseThrowException(token);

        User foundUser = userRepository.findByPlatformIdAndPlatformTypeOrElseThrowException(
                mailVO.getPlatformId(), mailVO.getPlatformType());
        foundUser.updateRole(Role.USER);
        userRepository.save(foundUser);

        return foundUser;
    }
}
