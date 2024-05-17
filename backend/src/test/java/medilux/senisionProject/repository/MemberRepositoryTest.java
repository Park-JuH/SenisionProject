package medilux.senisionProject.repository;

import medilux.senisionProject.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional
//@Rollback(false)
//class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    public void testMember() {
//        Member member = new Member("member1", 12);
//
//        Member savedMember = memberRepository.save(member);
//
//        Member findMember = memberRepository.findById(savedMember.getId()).get();
//
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
////        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
//        Assertions.assertThat(findMember.getAge()).isEqualTo(member.getAge());
//    }
//
//}