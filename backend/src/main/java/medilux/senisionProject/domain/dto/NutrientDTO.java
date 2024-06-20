package medilux.senisionProject.domain.dto;

import lombok.*;
import medilux.senisionProject.domain.Nutrient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NutrientDTO {

    private Double calcium;
    private Double protein;
    private Long calories;
    private Double vitaminA;


    public static NutrientDTO fromEntity(Nutrient nutrient) {
        return new NutrientDTO(
                nutrient.getCalcium(),
                nutrient.getProtein(),
                nutrient.getCalories(),
                nutrient.getVitaminA()
        );
    }

}
