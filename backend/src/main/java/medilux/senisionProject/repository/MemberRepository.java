package medilux.senisionProject.repository;

import medilux.senisionProject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    Boolean existsByPhone(String phone);

    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    Member findByPhone(String phone);

    Optional<Member> findById(Long userId);

    List<Member> findAll();
}
