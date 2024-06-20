package medilux.senisionProject.service;

import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import medilux.senisionProject.repository.MemberRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final MemberRepository memberRepository;
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetMemberScore(){
        memberRepository.findAll().forEach(member->{
            member.setScore(0);
        });
    }
}
