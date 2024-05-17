package medilux.senisionProject.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberDTO {

//    @Id
//    @GeneratedValue
//    @Column(name = "member_id")
    private Long id;

    //    @NonNull
    private String username;
    private String role;
    private String phone;

    private Date wakeup;
    private Date breakfast;
    private Date lunch;
    private Date dinner;
    private Date sleep;
    private String bodypart;


    //    @NonNull
//    private int age;

//    private String email;
}
