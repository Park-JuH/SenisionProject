package medilux.senisionProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
//@NoArgsConstructor()
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NonNull
    private String username;
    private String role;

    private String phone;

//    @NonNull
    private int age;

    private Date wakeup;
    private Date breakfast;
    private Date lunch;
    private Date dinner;
    private Date sleep;
    private String bodypart;

}
