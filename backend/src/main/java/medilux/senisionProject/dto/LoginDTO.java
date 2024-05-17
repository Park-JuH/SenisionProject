package medilux.senisionProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private MemberDTO memberDto;
    private String access;

    public LoginDTO (MemberDTO memberDto, String access) {
        this.memberDto = memberDto;
        this.access = access;
    }

}
