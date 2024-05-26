package medilux.senisionProject.domain.dto;

import kotlin.BuilderInference;
import lombok.*;
import medilux.senisionProject.domain.BodyPart;
import medilux.senisionProject.domain.Member;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

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

    public static MemberResponseDTO from(Member entity){
        return MemberResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .phone(entity.getPhone())
                .age(entity.getAge())
                .wakeup(entity.getWakeup())
                .breakfast(entity.getBreakfast())
                .lunch(entity.getLunch())
                .dinner(entity.getDinner())
                .sleep(entity.getSleep())
                .bodyPart(entity.getBodyPart())
                .build();
    }
}
