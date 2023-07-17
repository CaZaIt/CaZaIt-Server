package shop.cazait.domain.cafe.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafe.model.dto.ImageInformation;
import shop.cazait.domain.cafe.model.entity.Cafe;

@Schema(description = "관리중인 카페 정보 조회 Response")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ManageCafeListOutDTO {

    @JsonProperty
    @Schema(description = "카페 ID", example = "1")
    private Long cafeId;

    @Schema(description = "이름", example = "롬곡")
    private String name;

    @Schema(description = "위치", example = "서울특별시 광진구 군자동 광나루로17길 18")
    private String address;

    @JsonProperty
    @Schema(description = "이미지 url")
    private List<ImageInformation> cafeImages;


    public static ManageCafeListOutDTO of(Cafe cafe, List<ImageInformation> cafeImages) {
        return ManageCafeListOutDTO.builder()
                .cafeId(cafe.getId())
                .name(cafe.getName())
                .address(cafe.getAddress())
                .cafeImages(cafeImages)
                .build();
    }

}

