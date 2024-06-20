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
        this.calcium = 0.0;
        this.protein = 0.0;
        this.calories = 0L;
        this.vitaminA = 0.0;
    }

    private Double calcium;
    private Double protein;
    private Long calories;
    private Double vitaminA;
}
