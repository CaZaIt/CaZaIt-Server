package shop.cazait.domain.cafe.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.model.entity.Cafe;

@Schema(description = "카페 수정 Response : 카페 수정 후 받는 응답")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CafeUpdateOutDTO {

    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;

    public static CafeUpdateOutDTO of(Cafe cafe) {
        return CafeUpdateOutDTO.builder()
                .cafeId(cafe.getId())
                .build();
    }
}
