package medilux.senisionProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
public class Nutrient {

    @Id
    @GeneratedValue
    @Column(name = "nutrient_id")
    private Long id;

    public Nutrient() {
        this.calcium = 0L;
        this.protein = 0L;
        this.calories = 0L;
        this.vitaminA = 0L;
    }

    private Long calcium;
    private Long protein;
    private Long calories;
    private Long vitaminA;
}
