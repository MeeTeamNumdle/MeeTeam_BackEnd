package synk.meeteam.domain.recruitment.recruitment_post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentClosingService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void closing() {
        log.info("Start Auto Recruitment Closing Process");
        recruitmentPostRepository.updateIsCloseTrue();
        log.info("End Auto Recruitment Closing Process");
    }
}
