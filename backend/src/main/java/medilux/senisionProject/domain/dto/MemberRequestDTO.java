package medilux.senisionProject.domain.dto;

import lombok.Getter;
import lombok.Setter;
import medilux.senisionProject.domain.BodyPart;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberRequestDTO {
    private int age;
    private Time wakeup;
    private Time breakfast;
    private Time lunch;
    private Time dinner;
    private Time sleep;
    private List<BodyPart> bodyPart;
}
