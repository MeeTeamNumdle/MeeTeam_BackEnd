package synk.meeteam.infra.mail;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_SERVICE;
import static synk.meeteam.infra.mail.MailText.CHAR_SET;
import static synk.meeteam.infra.mail.MailText.FRONT_VERIFY_DOMAIN;
import static synk.meeteam.infra.mail.MailText.LOGO_URL;
import static synk.meeteam.infra.mail.MailText.MAIL_APPROVE_TEMPLATE;
import static synk.meeteam.infra.mail.MailText.MAIL_TITLE_APPROVE;
import static synk.meeteam.infra.mail.MailText.MAIL_TITLE_SIGNUP;
import static synk.meeteam.infra.mail.MailText.MAIL_VERIFY_TEMPLATE;
import static synk.meeteam.infra.mail.MailText.SENDER;
import static synk.meeteam.infra.mail.MailText.SENDER_ADDRESS;
import static synk.meeteam.infra.mail.MailText.SUB_TYPE;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.UserVO;
import synk.meeteam.infra.redis.repository.RedisUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisUserRepository redisUserRepository;

    private final TemplateEngine templateEngine;

    public String generateApproveMailForm(String userName, String postId, String postName, String roleName,
                                          String writerName, String link) {

        String logoUrl = LOGO_URL;

        // Thymeleaf context 생성
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("postName", postName);
        context.setVariable("postId", postId);
        context.setVariable("roleName", roleName);
        context.setVariable("writerName", writerName);
        context.setVariable("logoUrl", logoUrl);
        context.setVariable("link", link);

        // Thymeleaf 템플릿 엔진을 사용하여 변수 값을 주입하여 HTML 렌더링
        return templateEngine.process(MAIL_APPROVE_TEMPLATE, context);
    }

    public String generateEmailVerifyMailForm(String userName, String emailCode) {

        String verifyUrl = FRONT_VERIFY_DOMAIN + emailCode.toString();
        String logoUrl = LOGO_URL;

        // Thymeleaf context 생성
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("verifyUrl", verifyUrl);
        context.setVariable("logoUrl", logoUrl);

        // Thymeleaf 템플릿 엔진을 사용하여 변수 값을 주입하여 HTML 렌더링
        return templateEngine.process(MAIL_VERIFY_TEMPLATE, context);
    }

    @Transactional
    public void sendVerifyMail(String platformId, String receiverMail) {
        String newEmailCode = UUID.randomUUID().toString();

        UserVO userVO = redisUserRepository.findByPlatformIdOrElseThrowException(platformId);
        userVO.updateEmailCode(newEmailCode);
        redisUserRepository.save(userVO);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(RecipientType.TO, receiverMail);// 받는 대상
            message.setSubject(MAIL_TITLE_SIGNUP);// 제목

            String body = generateEmailVerifyMailForm(userVO.getName(), newEmailCode);

            message.setText(body, CHAR_SET, SUB_TYPE);// 내용, charset 타입, subtype
            // 보내는 사람의 이메일 주소, 보내는 사람 이름
            message.setFrom(new InternetAddress(SENDER_ADDRESS, SENDER));  // 보내는 대상
            mailSender.send(message); // 메일 전송
        } catch (Exception e) {
            log.info("{}", e);
            throw new AuthException(INVALID_MAIL_SERVICE);
        }
    }

    @Transactional
    public void sendApproveMails(Long postId, List<RecruitmentApplicant> applicants, String writerName) {
        if (applicants == null) {
            return;
        }
        MimeMessage message = mailSender.createMimeMessage();

        for (RecruitmentApplicant recruitmentApplicant : applicants) {
            User applicant = recruitmentApplicant.getApplicant();
            String receiverMail =
                    applicant.isUniversityMainEmail() ? applicant.getUniversityEmail() : applicant.getSubEmail();

            try {
                message.addRecipients(RecipientType.TO, receiverMail);// 받는 대상
                message.setSubject(MAIL_TITLE_APPROVE);// 제목

                String body = generateApproveMailForm(applicant.getName(), postId.toString(),
                        recruitmentApplicant.getRecruitmentPost().getTitle(),
                        recruitmentApplicant.getRole().getName(), writerName,
                        recruitmentApplicant.getRecruitmentPost().getKakaoLink());

                message.setText(body, CHAR_SET, SUB_TYPE);// 내용, charset 타입, subtype
                // 보내는 사람의 이메일 주소, 보내는 사람 이름
                message.setFrom(new InternetAddress(SENDER_ADDRESS, SENDER));// 보내는 대상
                mailSender.send(message); // 메일 전송
            } catch (Exception e) {
                log.info("{}", e);
                throw new AuthException(INVALID_MAIL_SERVICE);
            }
        }

    }

    @Transactional(readOnly = true)
    public UserVO verify(String emailCode) {
        return redisUserRepository.findByEmailCodeOrElseThrowException(emailCode);
    }
}
