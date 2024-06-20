package medilux.senisionProject.service;

import lombok.RequiredArgsConstructor;
import medilux.senisionProject.domain.AccessEntity;
import medilux.senisionProject.domain.Member;
import medilux.senisionProject.domain.Nutrient;
import medilux.senisionProject.domain.dto.*;
import medilux.senisionProject.jwt.JWTUtil;
import medilux.senisionProject.repository.AccessRepository;
import medilux.senisionProject.repository.MemberRepository;
import medilux.senisionProject.repository.NutrientRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final NutrientRepository nutrientRepository;

    private WebClient webClient;
    private final JWTUtil jwtUtil;
    private final AccessRepository accessRepository;
    @Transactional
    public Long joinProcess(JoinDTO joinDTO) {

        String phone = joinDTO.getPhone();

        Boolean isExist = memberRepository.existsByPhone(phone);

        if (isExist) {
            return null;
        }

        Member newMember = new Member();

        newMember.setPhone(phone);
        newMember.setRole("ROLE_USER");


        memberRepository.save(newMember);

        return newMember.getId();
    }
    @Transactional
    public LoginDTO loadUserByPhone(String phone) {

        Member member = memberRepository.findByPhone(phone);
        if (member == null) {
            return null; // Or throw an appropriate exception.
        }

        String newAccess = jwtUtil.createJwt(member.getPhone(), member.getRole(), 60 * 60 * 10L); // Ensure milliseconds are correctly calculated.
        Date expirationDate = new Date(System.currentTimeMillis() + (60 * 60 * 10L));

        AccessEntity accessEntity = new AccessEntity(); // Create new if doesn't exist.
        accessEntity.setPhone(phone);
        accessEntity.setAccess(newAccess);
        accessEntity.setExpiration(expirationDate.toString());
        accessRepository.save(accessEntity);

        return new LoginDTO(MemberResponseDTO.from(member), newAccess);
    }

    @Transactional
    public MemberResponseDTO saveMemberInfo(Member member, MemberRequestDTO memberRequestDTO){
        member.setAge(memberRequestDTO.getAge());
        member.setBreakfast(memberRequestDTO.getBreakfast());
        member.setLunch(memberRequestDTO.getLunch());
        member.setDinner(memberRequestDTO.getDinner());
        member.setWakeup(memberRequestDTO.getWakeup());
        member.setSleep(memberRequestDTO.getSleep());
        member.setBodyPart(memberRequestDTO.getBodyPart());

        memberRepository.save(member);
        return MemberResponseDTO.from(member);
    }

    public MemberResponseDTO findMemberByToken(Member member){
        return MemberResponseDTO.from(member);
    }

    @Transactional
    public NutrientDTO saveNutrientInfo(Long userId, MultipartFile file) {
        System.out.println("MemberService.updateNutrientInfo");
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8000") // You can parameterize or configure this URL as needed
                .build();

        Optional<Member> memberOptional = memberRepository.findById(userId);

//        if (!memberOptional.isPresent()) {
//            return null; // Or throw an appropriate exception
//        }

        Member member = memberOptional.get();
        Nutrient nutrient = member.getNutrient();
        try {
            // Assuming file needs to be sent to FastAPI and expecting JSON in return
            Map<String, Object> extract = webClient.post()
                    .uri("/api/v1/food/nutrition") // Modify this with the actual endpoint
                    .contentType(MediaType.MULTIPART_FORM_DATA) // 멀티파트 폼 데이터 설정
                    .body(BodyInserters.fromMultipartData("file", file.getResource())) // 파일을 요청의 일부로 전송
                    .retrieve() // Initiate the retrieval of the response
                    .onStatus(status -> status.isError(), // Correct way to check for error status
                            response -> Mono.error(new RuntimeException("Failed to send file: " + response.statusCode())))
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}) // Assuming the response can be directly converted to NutrientDTO
                    .block(); // Block to wait for the response because this is a synchronous method call

            Map<String, Object> foodNutrition = (Map<String, Object>) extract.get("food_nutrition");

            if (foodNutrition != null) {
                Double calcium = (Double) foodNutrition.getOrDefault("calcium", 0.0);
                Double protein = (Double) foodNutrition.getOrDefault("protein", 0.0);
                Integer calories = (Integer) foodNutrition.getOrDefault("calories", 0);
                Double vitaminA = (Double) foodNutrition.getOrDefault("vitaminA", 0.0);

                // 필요한 경우 nutrient 데이터 업데이트 (여기서는 가정)
                nutrient.setCalcium(nutrient.getCalcium() + calcium);
                nutrient.setProtein(nutrient.getProtein() + protein);
                nutrient.setCalories(nutrient.getCalories() + calories);
                nutrient.setVitaminA(nutrient.getVitaminA() + vitaminA);
            } else {
                throw new RuntimeException("No nutrition data found in response");
            }
        } catch (Exception e) {
            // 예외를 잡고 로깅합니다.
            System.err.println("Exception during webClient call: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update nutrient info", e);
        }
        // Save and return updated nutrient data
        nutrientRepository.save(nutrient);

        return new NutrientDTO(nutrient.getCalcium(), nutrient.getProtein(), nutrient.getCalories(), nutrient.getVitaminA());

    }
}
