package medilux.senisionProject.controller;

import medilux.senisionProject.dto.JoinDTO;
import medilux.senisionProject.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        Long newId = joinService.joinProcess(joinDTO);

        if (newId == null) {
            System.out.println("Failed to register user.");
            return ResponseEntity.badRequest().body("Registration failed");
        }

        return ResponseEntity.ok("User registered successfully with ID: " + newId);
    }

}
