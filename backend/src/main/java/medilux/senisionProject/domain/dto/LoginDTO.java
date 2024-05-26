package medilux.senisionProject.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private MemberResponseDTO memberResponseDto;
    private String access;

    public LoginDTO (MemberResponseDTO memberResponseDto, String access) {
        this.memberResponseDto = memberResponseDto;
        this.access = access;
    }

}
