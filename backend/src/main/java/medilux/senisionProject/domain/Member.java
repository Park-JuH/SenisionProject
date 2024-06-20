package medilux.senisionProject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String role;

    private String phone;
    private int age;

    private Time wakeup;
    private Time breakfast;
    private Time lunch;
    private Time dinner;
    private Time sleep;
    private List<BodyPart> bodyPart;

    private int score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    @PrePersist
    private void ensureNutrientPresent() {
        if (this.nutrient == null) {
            this.nutrient = new Nutrient();
        }
    }
}
