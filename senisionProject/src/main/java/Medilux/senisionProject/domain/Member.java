package Medilux.senisionProject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

@EntityScan
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
