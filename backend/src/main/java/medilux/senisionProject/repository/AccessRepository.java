package medilux.senisionProject.repository;

import medilux.senisionProject.domain.AccessEntity;
//import medilux.senisionProject.domain.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface AccessRepository extends JpaRepository<AccessEntity, Long> {

    AccessEntity findByPhone(String phone);
    Boolean existsByPhone(String access);

    @Transactional
    void deleteByPhone(String access);
}
