package shop.cazait.domain.cafe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.entity.Cafe;

@Schema(description = "카페 등록 Response : 카페 등록 후 받는 응답")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeCreateOutDTO {

    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;

    public static CafeCreateOutDTO of(Cafe cafe) {
        return CafeCreateOutDTO.builder()
                .cafeId(cafe.getId())
                .build();
    }

}
