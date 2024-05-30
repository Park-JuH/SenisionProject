package medilux.senisionProject.repository;

import medilux.senisionProject.domain.Member;
import medilux.senisionProject.domain.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

}
