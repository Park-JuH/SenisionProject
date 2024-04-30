package medilux.senisionProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor()
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NonNull
    private String name;

//    @NonNull
    private int age;

    private String email;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
